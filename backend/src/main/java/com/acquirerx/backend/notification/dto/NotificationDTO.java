package com.acquirerx.backend.notification.dto;

import java.time.LocalDateTime;

public class NotificationDTO {

    private Long notificationId;

    private Long userId;

    private String message;

    // Batch / Settlement / Dispute / Risk / Recon
    private String category;

    // Unread / Read / Dismissed
    private String status;

    private LocalDateTime createdDate;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public NotificationDTO() {
    }

    public NotificationDTO(Long notificationId, Long userId, String message,
                           String category, String status, LocalDateTime createdDate) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.message = message;
        this.category = category;
        this.status = status;
        this.createdDate = createdDate;
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
