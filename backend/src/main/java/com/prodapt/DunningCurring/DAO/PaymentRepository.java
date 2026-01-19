package com.prodapt.DunningCurring.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prodapt.DunningCurring.Entity.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByServiceId(Long serviceId);
}
