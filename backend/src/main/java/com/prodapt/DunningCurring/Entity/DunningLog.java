package com.prodapt.DunningCurring.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dunning_log")
public class DunningLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private TelecomService service;

    @ManyToOne
    @JoinColumn(name = "rule_id")
    private DunningRule rule;

    private String actionTaken;
    private String statusBefore;
    private String statusAfter;

    private LocalDateTime actionDate = LocalDateTime.now();

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

	public DunningRule getRule() {
		return rule;
	}

	public void setRule(DunningRule rule) {
		this.rule = rule;
	}

	public String getActionTaken() {
		return actionTaken;
	}

	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}

	public String getStatusBefore() {
		return statusBefore;
	}

	public void setStatusBefore(String statusBefore) {
		this.statusBefore = statusBefore;
	}

	public String getStatusAfter() {
		return statusAfter;
	}

	public void setStatusAfter(String statusAfter) {
		this.statusAfter = statusAfter;
	}

	public LocalDateTime getActionDate() {
		return actionDate;
	}

	public void setActionDate(LocalDateTime actionDate) {
		this.actionDate = actionDate;
	}

    // getters and setters
}

