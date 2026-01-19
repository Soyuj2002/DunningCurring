package com.prodapt.DunningCurring.DAO;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.prodapt.DunningCurring.Entity.TelecomService;

public interface TelecomServiceRepository extends JpaRepository<TelecomService, Long> {

    List<TelecomService> findByCustomerId(Long customerId);
 // 1. Count by Status
    long countByStatus	(String Status);
 // 2. Sum of all overdue money
    @Query("SELECT SUM(t.currentOverdueAmount) FROM TelecomService t")
    BigDecimal sumTotalOverdue();
    
    List<TelecomService> findByStatus(String status);

    List<TelecomService> findByNextDueDateBeforeAndStatusNot(java.time.LocalDate date, String status);
}

