package com.prodapt.DunningCurring.Controller;



import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prodapt.DunningCurring.DAO.UserRepository;
import com.prodapt.DunningCurring.Entity.User;
import com.prodapt.DunningCurring.Service.ActivityLogService;
import com.prodapt.DunningCurring.Service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    
//    Injecting Logging Dependencies
    @Autowired
    private ActivityLogService activityLogService;
    
    @Autowired
    private UserRepository userRepository;

    // Make payment
    @PostMapping("/pay/{billId}")
    public String payBill(@PathVariable Long billId,
                          @RequestParam BigDecimal amount,
                          @RequestParam String mode) {

        // 1. Process the payment logic (Update Bill, Service Status, Cure Event)
        paymentService.processPayment(billId, amount, mode);

        // 2. Log the Activity (Auditing)
        // We log it as 'admin_super' or 'system' since the system is auto-restoring services
        User systemUser = userRepository.findByUsername("admin_super").orElse(null);
        if(systemUser != null) {
            String logDescription = "Bill #" + billId + " Paid via " + mode + ". Service Restored.";
            activityLogService.log(systemUser, "PAYMENT_RECEIVED", "Bill", billId);
        }

        return "Payment successful. Service restored.";
    }
    
}
