package com.acquirerx.backend.notification.service;

import com.acquirerx.backend.exception.ResourceNotFoundException;
import com.acquirerx.backend.notification.dto.NotificationDTO;
import com.acquirerx.backend.notification.entity.Notification;
import com.acquirerx.backend.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // -------------------------------------------------------------------------
    // Create a notification
    // -------------------------------------------------------------------------

    public Notification createNotification(NotificationDTO dto) {
        Notification notification = new Notification();
        notification.setUserId(dto.getUserId());
        notification.setMessage(dto.getMessage());
        notification.setCategory(dto.getCategory());
        notification.setCreatedDate(LocalDateTime.now());
        // Default status to "Unread" if not provided
        notification.setStatus(dto.getStatus() == null ? "Unread" : dto.getStatus());
        return notificationRepository.save(notification);
    }

    // -------------------------------------------------------------------------
    // Retrieve notifications
    // -------------------------------------------------------------------------

    public List<Notification> getNotificationsByUser(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found: " + id));
    }

    public List<Notification> getByCategory(String category) {
        return notificationRepository.findByCategory(category);
    }

    // -------------------------------------------------------------------------
    // Status transitions
    // -------------------------------------------------------------------------

    public Notification markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found: " + id));
        notification.setStatus("Read");
        return notificationRepository.save(notification);
    }

    /**
     * Marks every notification belonging to the given user as Read.
     *
     * @return a human-readable count string, e.g. "3 notifications marked as read"
     */
    public String markAllAsRead(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        for (Notification notification : notifications) {
            notification.setStatus("Read");
        }
        notificationRepository.saveAll(notifications);
        return notifications.size() + " notifications marked as read";
    }

    public Notification dismiss(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found: " + id));
        notification.setStatus("Dismissed");
        return notificationRepository.save(notification);
    }
}
