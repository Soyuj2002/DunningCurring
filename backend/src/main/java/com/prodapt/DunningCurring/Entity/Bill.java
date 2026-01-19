package com.prodapt.DunningCurring.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "service_id")
    private TelecomService service;
    private BigDecimal billAmount;
    private LocalDate billDate;
    private LocalDate dueDate;
    private boolean paid;
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
	public BigDecimal getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(BigDecimal billAmount) {
		this.billAmount = billAmount;
	}
	public LocalDate getBillDate() {
		return billDate;
	}
	public void setBillDate(LocalDate billDate) {
		this.billDate = billDate;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public Bill(Long id, TelecomService service, BigDecimal billAmount, LocalDate billDate, LocalDate dueDate, boolean paid) {
		super();
		this.id = id;
		this.service = service;
		this.billAmount = billAmount;
		this.billDate = billDate;
		this.dueDate = dueDate;
		this.paid = paid;
	}
	public Bill(TelecomService service, BigDecimal billAmount, LocalDate billDate, LocalDate dueDate, boolean paid) {
		super();
		this.service = service;
		this.billAmount = billAmount;
		this.billDate = billDate;
		this.dueDate = dueDate;
		this.paid = paid;
	}
	public Bill() {
		super();
	}
	@Override
	public String toString() {
		return "Bill [id=" + id + ", service=" + service + ", billAmount=" + billAmount + ", billDate=" + billDate
				+ ", dueDate=" + dueDate + ", paid=" + paid + "]";
	}


    // getters and setters
    
}

