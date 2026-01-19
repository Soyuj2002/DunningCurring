package com.prodapt.DunningCurring.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;

    @Column(unique = true, nullable = false)
    private String customerCode;

    private String email;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<TelecomService> services;

    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<TelecomService> getServices() {
        return services;
    }

    public void setServices(List<TelecomService> services) {
        this.services = services;
    }

    // ===== Constructors =====

    public Customer() {}

    public Customer(User user, String name, String customerCode, String email) {
        this.user = user;
        this.name = name;
        this.customerCode = customerCode;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer [id=" + id +
                ", userId=" + (user != null ? user.getId() : null) +
                ", name=" + name +
                ", customerCode=" + customerCode +
                ", email=" + email +
                ", createdAt=" + createdAt + "]";
    }
}
