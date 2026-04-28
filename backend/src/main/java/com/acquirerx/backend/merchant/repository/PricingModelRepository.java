package com.acquirerx.backend.merchant.repository;

import com.acquirerx.backend.merchant.entity.PricingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PricingModelRepository extends JpaRepository<PricingModel, Long> {
    List<PricingModel> findByMerchant_MerchantID(Long merchantId);
}
