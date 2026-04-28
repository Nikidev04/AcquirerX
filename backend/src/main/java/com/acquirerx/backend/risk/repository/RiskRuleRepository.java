package com.acquirerx.backend.risk.repository;

import com.acquirerx.backend.risk.entity.RiskRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiskRuleRepository extends JpaRepository<RiskRule, Long> {

    List<RiskRule> findByStatus(String status);
}
