package com.acquirerx.backend.reconciliation.repository;

import com.acquirerx.backend.reconciliation.entity.ReconItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReconItemRepository extends JpaRepository<ReconItem, Long> {
    List<ReconItem> findByReconFile_ReconFileId(Long reconFileId);
    List<ReconItem> findByMatchStatus(ReconItem.MatchStatus matchStatus);
}
