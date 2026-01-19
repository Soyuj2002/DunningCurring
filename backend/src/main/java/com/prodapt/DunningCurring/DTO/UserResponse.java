package com.prodapt.DunningCurring.DTO;
import com.prodapt.DunningCurring.Entity.User;
import java.time.LocalDateTime;

public class UserResponse {
    
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String role;
    private String status;
    private LocalDateTime createdAt;

    // Default Constructor
    public UserResponse() {
    }

    // Parameterized Constructor
    public UserResponse(Long id, String username, String email, String phone, 
                       String role, String status, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Static Factory Method to convert User entity to UserResponse
    public static UserResponse fromUser(User user) {
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getPhone(),
            user.getRole(),
            user.getStatus(),
            user.getCreatedAt()
        );
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "UserResponse [id=" + id + ", username=" + username + ", email=" + email + 
               ", phone=" + phone + ", role=" + role + ", status=" + status + 
               ", createdAt=" + createdAt + "]";
    }
}