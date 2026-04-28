package com.acquirerx.backend.merchant.repository;

import com.acquirerx.backend.merchant.entity.SettlementProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettlementProfileRepository extends JpaRepository<SettlementProfile, Long> {
    Optional<SettlementProfile> findByMerchant_MerchantID(Long merchantId);
}
