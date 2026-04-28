package com.acquirerx.backend.iam.controller;

import com.acquirerx.backend.iam.entity.AuditLog;
import com.acquirerx.backend.iam.repository.AuditLogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/iam/audit-logs")
public class AuditLogController {

    private final AuditLogRepository auditLogRepository;

    public AuditLogController(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    // GET /iam/audit-logs
    @GetMapping
    public ResponseEntity<List<AuditLog>> getAllAuditLogs() {
        return ResponseEntity.ok(auditLogRepository.findAll());
    }

    // GET /iam/audit-logs/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByUser(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(auditLogRepository.findByUser_UserId(userId));
    }

    // GET /iam/audit-logs/action/{action}
    @GetMapping("/action/{action}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByAction(@PathVariable("action") String action) {
        return ResponseEntity.ok(auditLogRepository.findByAction(action));
    }
}
