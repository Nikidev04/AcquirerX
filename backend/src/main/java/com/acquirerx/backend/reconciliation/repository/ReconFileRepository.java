package com.acquirerx.backend.reconciliation.repository;

import com.acquirerx.backend.reconciliation.entity.ReconFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReconFileRepository extends JpaRepository<ReconFile, Long> {
    List<ReconFile> findBySource(String source);
}
