package com.prodapt.DunningCurring.Controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prodapt.DunningCurring.DAO.CustomerRepository;
import com.prodapt.DunningCurring.DAO.TelecomServiceRepository;
import com.prodapt.DunningCurring.DTO.DashboardStats;

@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TelecomServiceRepository serviceRepository;

    @GetMapping("/stats")
    public DashboardStats getStats() {
        long totalCust = customerRepository.count();
        long activeCount = serviceRepository.countByStatus("ACTIVE");
        long blockedCount = serviceRepository.countByStatus("BLOCKED");
        
        BigDecimal totalRisk = serviceRepository.sumTotalOverdue();
        if (totalRisk == null) totalRisk = BigDecimal.ZERO; // Handle null if table is empty

        return new DashboardStats(totalCust, activeCount, blockedCount, totalRisk);
    }
}