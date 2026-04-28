package com.acquirerx.backend.fee.repository;

import com.acquirerx.backend.fee.entity.FeeRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface FeeRuleRepository extends JpaRepository<FeeRule, Long> {

    @Query("SELECT r FROM FeeRule r WHERE r.status = 'ACTIVE'")
    List<FeeRule> findAllActiveRules();

    List<FeeRule> findByStatus(String status);
}
