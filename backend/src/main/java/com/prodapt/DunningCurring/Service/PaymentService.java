package com.prodapt.DunningCurring.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prodapt.DunningCurring.DAO.*;
import com.prodapt.DunningCurring.Entity.*;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private TelecomServiceRepository telecomServiceRepository;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private CureEventRepository cureEventRepository;

    @Transactional // Ensures all updates happen together or fail together
    public void processPayment(Long billId, BigDecimal amount, String mode) {

        // 1. Find the Bill
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found with ID: " + billId));

        TelecomService service = bill.getService();
        String oldStatus = service.getStatus(); // Capture old status for logging

        // 2. Create Payment Record
        Payment payment = new Payment();
        payment.setBill(bill);
        payment.setService(service);
        payment.setAmount(amount);
        payment.setPaymentMode(mode);
        payment.setPaymentStatus("SUCCESS");
        payment.setPaymentDate(LocalDateTime.now()); // Ensure you have this field or use @PrePersist
        paymentRepository.save(payment);

        // 3. Mark Bill as Paid
        bill.setPaid(true);
        billRepository.save(bill);

        // 4. RESTORE SERVICE (The Curing Logic)
        // Reset overdue amount and set status back to Active
        service.setCurrentOverdueAmount(BigDecimal.ZERO);
        service.setStatus("ACTIVE");
        service.setCuringStatus("CURED"); // Mark as cured so Dunning knows to stop
        telecomServiceRepository.save(service);

        // 5. Create Cure Event (History of the restoration)
        CureEvent cure = new CureEvent();
        cure.setService(service);
        cure.setOverdueAmount(amount); // The amount that was cleared
        cure.setPreviousStatus(oldStatus);
        cure.setNewStatus("ACTIVE");
        cure.setCureDate(LocalDateTime.now());
        cureEventRepository.save(cure);
    }
}