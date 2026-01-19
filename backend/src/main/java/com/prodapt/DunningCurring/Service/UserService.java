package com.prodapt.DunningCurring.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prodapt.DunningCurring.DAO.CustomerRepository;
import com.prodapt.DunningCurring.DAO.UserRepository;
import com.prodapt.DunningCurring.DTO.CreateUserRequest;
import com.prodapt.DunningCurring.Entity.Customer;
import com.prodapt.DunningCurring.Entity.User;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /* ===================== HELPER ===================== */

    private User getCurrentAdmin() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                return userRepository.findByUsername(auth.getName()).orElse(null);
            }
        } catch (Exception e) {
            System.err.println("Could not fetch admin: " + e.getMessage());
        }
        return null;
    }

    /* ===================== BASIC QUERIES ===================== */

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role.toUpperCase());
    }

    /* ===================== CREATE USER ===================== */

    @Transactional
    public User createUser(CreateUserRequest request) {

        // Username check
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Email check
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        String role = request.getRole().toUpperCase();
        if (!role.equals("ADMIN") && !role.equals("CUSTOMER")) {
            throw new RuntimeException("Invalid role");
        }

        // Create User
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        user.setStatus("ACTIVE");

        User savedUser = userRepository.save(user);

        // ✅ CREATE CUSTOMER IF ROLE = CUSTOMER
        if ("CUSTOMER".equals(role)) {
            Customer customer = new Customer();
            customer.setUser(savedUser);
            customer.setName(savedUser.getUsername());
            customer.setEmail(savedUser.getEmail());
            customer.setCustomerCode("CUST_" + savedUser.getId());

            customerRepository.save(customer);
        }

        // Log activity
        try {
            User admin = getCurrentAdmin();
            if (admin != null) {
                activityLogService.log(admin, "CREATE_USER", "User", savedUser.getId());
            }
        } catch (Exception e) {
            System.err.println("CREATE_USER log failed: " + e.getMessage());
        }

        return savedUser;
    }

    /* ===================== UPDATE USER ===================== */

    @Transactional
    public User updateUser(Long id, CreateUserRequest request) {

        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getUsername().equals(request.getUsername())
            && userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (!user.getEmail().equals(request.getEmail())
            && userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole().toUpperCase());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User updatedUser = userRepository.save(user);

        try {
            User admin = getCurrentAdmin();
            if (admin != null) {
                activityLogService.log(admin, "UPDATE_USER", "User", updatedUser.getId());
            }
        } catch (Exception e) {
            System.err.println("UPDATE_USER log failed: " + e.getMessage());
        }

        return updatedUser;
    }

    /* ===================== DELETE USER ===================== */

    @Transactional
    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }

        // ✅ FIX: Delete associated customer first to avoid foreign key constraint
        try {
            Optional<Customer> customer = customerRepository.findByUserId(id);
            if (customer.isPresent()) {
                System.out.println("Deleting associated customer for user ID: " + id);
                customerRepository.delete(customer.get());
            }
        } catch (Exception e) {
            System.err.println("Failed to delete associated customer: " + e.getMessage());
            // Continue with user deletion even if customer deletion fails
        }

        // Log activity
        try {
            User admin = getCurrentAdmin();
            if (admin != null) {
                activityLogService.log(admin, "DELETE_USER", "User", id);
            }
        } catch (Exception e) {
            System.err.println("DELETE_USER log failed: " + e.getMessage());
        }

        // Delete user
        userRepository.deleteById(id);
        System.out.println("User deleted successfully: " + id);
    }

    /* ===================== ACTIVATE / DEACTIVATE ===================== */

    @Transactional
    public User activateUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus("ACTIVE");
        
        try {
            User admin = getCurrentAdmin();
            if (admin != null) {
                activityLogService.log(admin, "ACTIVATE_USER", "User", id);
            }
        } catch (Exception e) {
            System.err.println("ACTIVATE_USER log failed: " + e.getMessage());
        }
        
        return userRepository.save(user);
    }

    @Transactional
    public User deactivateUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus("INACTIVE");
        
        try {
            User admin = getCurrentAdmin();
            if (admin != null) {
                activityLogService.log(admin, "DEACTIVATE_USER", "User", id);
            }
        } catch (Exception e) {
            System.err.println("DEACTIVATE_USER log failed: " + e.getMessage());
        }
        
        return userRepository.save(user);
    }
}