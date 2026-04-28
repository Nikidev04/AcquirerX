package com.acquirerx.backend.notification.controller;

import com.acquirerx.backend.notification.dto.NotificationDTO;
import com.acquirerx.backend.notification.entity.Notification;
import com.acquirerx.backend.notification.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // -------------------------------------------------------------------------
    // POST /api/notifications  – Create a notification
    // -------------------------------------------------------------------------

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody NotificationDTO dto) {
        Notification notification = notificationService.createNotification(dto);
        return new ResponseEntity<>(notification, HttpStatus.CREATED);
    }

    // -------------------------------------------------------------------------
    // GET /api/notifications/user/{userId}  – All notifications for a user
    // -------------------------------------------------------------------------

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsByUser(@PathVariable("userId") Long userId) {
        List<Notification> notifications = notificationService.getNotificationsByUser(userId);
        return ResponseEntity.ok(notifications);
    }

    // -------------------------------------------------------------------------
    // GET /api/notifications/{notificationId}  – Single notification by ID
    // -------------------------------------------------------------------------

    @GetMapping("/{notificationId}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable("notificationId") Long notificationId) {
        Notification notification = notificationService.getNotificationById(notificationId);
        return ResponseEntity.ok(notification);
    }

    // -------------------------------------------------------------------------
    // PUT /api/notifications/{notificationId}/read  – Mark one as Read
    // -------------------------------------------------------------------------

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<Notification> markAsRead(@PathVariable("notificationId") Long notificationId) {
        Notification notification = notificationService.markAsRead(notificationId);
        return ResponseEntity.ok(notification);
    }

    // -------------------------------------------------------------------------
    // PUT /api/notifications/read-all/{userId}  – Mark all as Read for a user
    // -------------------------------------------------------------------------

    @PutMapping("/read-all/{userId}")
    public ResponseEntity<String> markAllAsRead(@PathVariable("userId") Long userId) {
        String result = notificationService.markAllAsRead(userId);
        return ResponseEntity.ok(result);
    }

    // -------------------------------------------------------------------------
    // PUT /api/notifications/{notificationId}/dismiss  – Dismiss a notification
    // -------------------------------------------------------------------------

    @PutMapping("/{notificationId}/dismiss")
    public ResponseEntity<Notification> dismiss(@PathVariable("notificationId") Long notificationId) {
        Notification notification = notificationService.dismiss(notificationId);
        return ResponseEntity.ok(notification);
    }

    // -------------------------------------------------------------------------
    // GET /api/notifications/category/{category}  – Filter by category
    // -------------------------------------------------------------------------

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Notification>> getByCategory(@PathVariable("category") String category) {
        List<Notification> notifications = notificationService.getByCategory(category);
        return ResponseEntity.ok(notifications);
    }
}
