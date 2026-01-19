package com.prodapt.DunningCurring.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.prodapt.DunningCurring.DAO.CustomerRepository;
import com.prodapt.DunningCurring.DAO.UserRepository;
import com.prodapt.DunningCurring.Entity.Customer;
import com.prodapt.DunningCurring.Entity.User;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Get profile for logged-in customer
    @GetMapping("/my-profile")
    @Transactional(readOnly = true) // ensures services are loaded if LAZY
    public Customer getMyProfile(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));

        return customerRepository.findByUserId(user.getId())
            .orElseThrow(() -> new RuntimeException("Customer not found"));
    }
}
