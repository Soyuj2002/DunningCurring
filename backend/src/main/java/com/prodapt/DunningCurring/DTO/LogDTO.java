package com.prodapt.DunningCurring.DTO;

import java.time.LocalDateTime;

public class LogDTO {
    private Long id;
    private String username; 
    private String action;
    private String entityType;
    private Long entityId;
    private LocalDateTime timestamp;

    // Constructor
    public LogDTO(Long id, String username, String action, String entityType, Long entityId, LocalDateTime timestamp) {
        this.id = id;
        this.username = username;
        this.action = action;
        this.entityType = entityType;
        this.entityId = entityId;
        this.timestamp = timestamp;
    }

    // Getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getAction() { return action; }
    public String getEntityType() { return entityType; }
    public Long getEntityId() { return entityId; }
    public LocalDateTime getTimestamp() { return timestamp; }
}