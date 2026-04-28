package com.acquirerx.backend.settlement.repository;

import com.acquirerx.backend.settlement.entity.SettlementBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for SettlementBatch.
 * If this is RED in your IDE, ensure the import above matches the Entity package.
 */
@Repository
public interface SettlementBatchRepository extends JpaRepository<SettlementBatch, Long> {
    List<SettlementBatch> findByMerchantId(Long merchantId);
    List<SettlementBatch> findByStatus(String status);
}