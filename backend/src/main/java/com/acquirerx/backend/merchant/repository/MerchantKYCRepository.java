package com.acquirerx.backend.merchant.repository;

import com.acquirerx.backend.merchant.entity.MerchantKYC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantKYCRepository extends JpaRepository<MerchantKYC, Long> {
    Optional<MerchantKYC> findByMerchant_MerchantID(Long merchantId);
}
