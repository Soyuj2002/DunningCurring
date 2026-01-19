package com.prodapt.DunningCurring.DTO;

import java.math.BigDecimal;

public class DashboardStats {
    private long totalCustomers;
    private long activeServices;
    private long blockedServices;
    private BigDecimal totalRevenueAtRisk;

    // Constructor
    public DashboardStats(long totalCustomers, long activeServices, long blockedServices, BigDecimal totalRevenueAtRisk) {
        this.totalCustomers = totalCustomers;
        this.activeServices = activeServices;
        this.blockedServices = blockedServices;
        this.totalRevenueAtRisk = totalRevenueAtRisk;
    }
    // Getters and Setters
    public long getTotalCustomers() { return totalCustomers; }
    public void setTotalCustomers(long totalCustomers) { this.totalCustomers = totalCustomers; }
    
    public long getActiveServices() { return activeServices; }
    public void setActiveServices(long activeServices) { this.activeServices = activeServices; }

    public long getBlockedServices() { return blockedServices; }
    public void setBlockedServices(long blockedServices) { this.blockedServices = blockedServices; }

    public BigDecimal getTotalRevenueAtRisk() { return totalRevenueAtRisk; }
    public void setTotalRevenueAtRisk(BigDecimal totalRevenueAtRisk) { this.totalRevenueAtRisk = totalRevenueAtRisk; }
}