package com.prodapt.DunningCurring.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "service")
public class TelecomService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    private String serviceType;     // MOBILE / BROADBAND
    private String identifier;      // phone / broadband id
    private String status;          // ACTIVE / RESTRICTED / BLOCKED

    private BigDecimal currentOverdueAmount;
    private LocalDate lastPaymentDate;
    private LocalDate nextDueDate;
    
    private String billingType; // PREPAID or POSTPAID    

    private String curingStatus;    // OPEN / CURED

    private LocalDateTime createdAt = LocalDateTime.now();
    
    

	public TelecomService(Long id, Customer customer, String serviceType, String identifier, String status,
			BigDecimal currentOverdueAmount, LocalDate lastPaymentDate, LocalDate nextDueDate, String curingStatus,
			LocalDateTime createdAt, String billingType) {
		super();
		this.id = id;
		this.customer = customer;
		this.serviceType = serviceType;
		this.identifier = identifier;
		this.status = status;
		this.currentOverdueAmount = currentOverdueAmount;
		this.lastPaymentDate = lastPaymentDate;
		this.nextDueDate = nextDueDate;
		this.curingStatus = curingStatus;
		this.createdAt = createdAt;
		this.billingType=billingType;
	}

	public TelecomService(Customer customer, String serviceType, String identifier, String status,
			BigDecimal currentOverdueAmount, LocalDate lastPaymentDate, LocalDate nextDueDate, String curingStatus,
			LocalDateTime createdAt, String billingType) {
		super();
		this.customer = customer;
		this.serviceType = serviceType;
		this.identifier = identifier;
		this.status = status;
		this.currentOverdueAmount = currentOverdueAmount;
		this.lastPaymentDate = lastPaymentDate;
		this.nextDueDate = nextDueDate;
		this.curingStatus = curingStatus;
		this.createdAt = createdAt;
		this.billingType=billingType;
	}

	public TelecomService() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getCurrentOverdueAmount() {
		return currentOverdueAmount;
	}

	public void setCurrentOverdueAmount(BigDecimal currentOverdueAmount) {
		this.currentOverdueAmount = currentOverdueAmount;
	}

	public LocalDate getLastPaymentDate() {
		return lastPaymentDate;
	}

	public void setLastPaymentDate(LocalDate lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}

	public LocalDate getNextDueDate() {
		return nextDueDate;
	}

	public void setNextDueDate(LocalDate nextDueDate) {
		this.nextDueDate = nextDueDate;
	}

	public String getCuringStatus() {
		return curingStatus;
	}

	public void setCuringStatus(String curingStatus) {
		this.curingStatus = curingStatus;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getBillingType() { return billingType; }
	public void setBillingType(String billingType) { this.billingType = billingType;
	
	}

	@Override
	public String toString() {
		return "Service [id=" + id + ", customer=" + customer + ", serviceType=" + serviceType + ", identifier="
				+ identifier + ", status=" + status + ", currentOverdueAmount=" + currentOverdueAmount
				+ ", lastPaymentDate=" + lastPaymentDate + ", nextDueDate=" + nextDueDate +"billingType="+billingType+ ", curingStatus="
				+ curingStatus + ", createdAt=" + createdAt + "]";
	}
    
    

    // getters and setters
}

