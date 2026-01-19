package com.prodapt.DunningCurring.Controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prodapt.DunningCurring.Entity.TelecomService;
import com.prodapt.DunningCurring.Service.TelecomServiceService;

@RestController
@RequestMapping("/api/services")
public class TelecomServiceController {

	@Autowired
	private TelecomServiceService telecomServiceService;

	// Get service by ID
	@GetMapping("/{id}")
	public TelecomService getService(@PathVariable Long id) {
		return telecomServiceService.getService(id);
	}

	// Get all services of a customer
	@GetMapping("/customer/{customerId}")
	public List<TelecomService> getCustomerServices(@PathVariable Long customerId) {
//		System.out.println("🚨 CONTROLLER HIT: Fetching services for Customer ID: " + customerId);
		return telecomServiceService.getServicesByCustomer(customerId);
	}

	// Update overdue amount
	@PutMapping("/{id}/overdue")
	public String updateOverdue(@PathVariable Long id, @RequestParam BigDecimal amount) {

		telecomServiceService.updateOverdue(id, amount);
		return "Overdue updated successfully";
	}

	@PostMapping
	public TelecomService createTelecomService(@RequestBody TelecomService service) {
		return telecomServiceService.createService(service);
	}
	
	// Changed from @PatchMapping to @PutMapping
    @PutMapping("/{id}/status")
    public TelecomService updateStatus(
            @PathVariable Long id, 
            @RequestParam String status) {
            
        return telecomServiceService.updateServiceStatus(id, status);
    }}
