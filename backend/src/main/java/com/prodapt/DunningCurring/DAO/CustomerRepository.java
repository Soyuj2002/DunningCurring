package com.prodapt.DunningCurring.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prodapt.DunningCurring.Entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByCustomerCode(String customerCode);
    
    Customer findByEmail(String email);
    
    Optional<Customer> findByUserId(Long userId);
}

	