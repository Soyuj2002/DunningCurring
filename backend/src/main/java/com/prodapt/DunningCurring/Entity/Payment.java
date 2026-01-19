package com.prodapt.DunningCurring.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private TelecomService service;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;

    private BigDecimal amount;
    private String paymentMode;     // UPI / CARD / NETBANKING
    private String paymentStatus;   // SUCCESS / FAILED

    private LocalDateTime paymentDate = LocalDateTime.now();

	public Payment(Long id, TelecomService service, Bill bill, BigDecimal amount, String paymentMode, String paymentStatus,
			LocalDateTime paymentDate) {
		super();
		this.id = id;
		this.service = service;
		this.bill = bill;
		this.amount = amount;
		this.paymentMode = paymentMode;
		this.paymentStatus = paymentStatus;
		this.paymentDate = paymentDate;
	}

	public Payment(TelecomService service, Bill bill, BigDecimal amount, String paymentMode, String paymentStatus,
			LocalDateTime paymentDate) {
		super();
		this.service = service;
		this.bill = bill;
		this.amount = amount;
		this.paymentMode = paymentMode;
		this.paymentStatus = paymentStatus;
		this.paymentDate = paymentDate;
	}

	public Payment() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TelecomService getService() {
		return service;
	}

	public void setService(TelecomService service) {
		this.service = service;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", service=" + service + ", bill=" + bill + ", amount=" + amount + ", paymentMode="
				+ paymentMode + ", paymentStatus=" + paymentStatus + ", paymentDate=" + paymentDate + "]";
	}
	

    // getters and setters
}
