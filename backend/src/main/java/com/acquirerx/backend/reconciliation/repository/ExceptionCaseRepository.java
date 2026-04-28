package com.acquirerx.backend.reconciliation.repository;

import com.acquirerx.backend.reconciliation.entity.ExceptionCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExceptionCaseRepository extends JpaRepository<ExceptionCase, Long> {
    List<ExceptionCase> findByStatus(ExceptionCase.Status status);
}
