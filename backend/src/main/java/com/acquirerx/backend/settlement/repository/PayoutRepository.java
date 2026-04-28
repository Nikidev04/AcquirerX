package com.acquirerx.backend.settlement.repository;

import com.acquirerx.backend.settlement.entity.Payout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayoutRepository extends JpaRepository<Payout, Long> {
    List<Payout> findByStatus(String status);
    List<Payout> findBySettleBatchId(Long settleBatchId);
}