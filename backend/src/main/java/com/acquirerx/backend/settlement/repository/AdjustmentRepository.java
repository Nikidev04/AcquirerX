package com.acquirerx.backend.settlement.repository;

import com.acquirerx.backend.settlement.entity.Adjustment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AdjustmentRepository extends JpaRepository<Adjustment, Long> {
    List<Adjustment> findByMerchantIdAndStatus(Long merchantId, String status);
    List<Adjustment> findByMerchantId(Long merchantId);
}