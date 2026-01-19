package com.prodapt.DunningCurring.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cure_event")
public class CureEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private TelecomService service;
    private BigDecimal overdueAmount;
    private String previousStatus;
    private String newStatus;
    private LocalDateTime cureDate;
    
    public LocalDateTime getCureDate() {
		return cureDate;
	}

	public void setCureDate(LocalDateTime cureDate) {
		this.cureDate = cureDate;
	}

	private LocalDateTime curedOn = LocalDateTime.now();

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

	public BigDecimal getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(BigDecimal overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	public String getPreviousStatus() {
		return previousStatus;
	}

	public void setPreviousStatus(String previousStatus) {
		this.previousStatus = previousStatus;
	}

	public String getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}

	public LocalDateTime getCuredOn() {
		return curedOn;
	}

	public void setCuredOn(LocalDateTime curedOn) {
		this.curedOn = curedOn;
	}

    // getters and setters
    
}

