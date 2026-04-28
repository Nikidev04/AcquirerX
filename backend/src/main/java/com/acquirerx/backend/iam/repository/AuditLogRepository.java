package com.acquirerx.backend.iam.repository;

import com.acquirerx.backend.iam.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {

    List<AuditLog> findByUser_UserId(Integer userId);

    List<AuditLog> findByAction(String action);
}
