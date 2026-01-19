package com.prodapt.DunningCurring.Service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.DunningCurring.DAO.BillRepository;
import com.prodapt.DunningCurring.DAO.TelecomServiceRepository;
import com.prodapt.DunningCurring.Entity.TelecomService;

@Service
public class TelecomServiceService {

    private final TelecomServiceRepository telecomServiceRepository;
    private final BillRepository billRepository;

    @Autowired
    public TelecomServiceService(TelecomServiceRepository telecomServiceRepository, BillRepository billRepository) {
        this.telecomServiceRepository = telecomServiceRepository;
        this.billRepository = billRepository;
    }

    // Fetch all services for a customer
    public List<TelecomService> getServicesByCustomer(Long customerId) {
        return telecomServiceRepository.findByCustomerId(customerId);
    }

    // Fetch single service
    public TelecomService getService(Long serviceId) {
        return telecomServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
    }

    // Update overdue amount
    public void updateOverdue(Long serviceId, BigDecimal overdueAmount) {
        TelecomService service = getService(serviceId);
        service.setCurrentOverdueAmount(overdueAmount);
        telecomServiceRepository.save(service);
    }

    // Update service status manually
    public void updateStatus(TelecomService service, String status) {
        service.setStatus(status);
        telecomServiceRepository.save(service);
    }

    // Check if payment is allowed
    public boolean isPaymentAllowed(TelecomService service) {
        return !"BLOCKED".equalsIgnoreCase(service.getStatus());
    }

    // Create new service
    public TelecomService createService(TelecomService service) {
        if (service.getStatus() == null) service.setStatus("ACTIVE");
        if (service.getCuringStatus() == null) service.setCuringStatus("NORMAL");
        return telecomServiceRepository.save(service);
    }

    // Update service status manually
    public TelecomService updateServiceStatus(Long serviceId, String newStatus) {
        TelecomService service = getService(serviceId);
        service.setStatus(newStatus.toUpperCase());

        switch (newStatus.toUpperCase()) {
            case "ACTIVE":
                service.setCuringStatus("NORMAL");
                break;
            case "BLOCKED":
                service.setCuringStatus("BLOCKED");
                break;
            case "RESTRICTED":
                service.setCuringStatus("OPEN");
                break;
            default:
                service.setCuringStatus("NORMAL");
        }

        return telecomServiceRepository.save(service);
    }

    // 🔥 Update service status based on unpaid bills
    public void updateServiceStatusBasedOnBills(Long serviceId) {
        TelecomService service = getService(serviceId);
        boolean hasUnpaidBills = !billRepository.findByServiceIdAndPaidFalse(serviceId).isEmpty();

        if (hasUnpaidBills) {
            service.setStatus("BLOCKED");
            service.setCuringStatus("OPEN");
        } else {
            service.setStatus("ACTIVE");
            service.setCuringStatus("NORMAL");
        }

        telecomServiceRepository.save(service);
    }
}
