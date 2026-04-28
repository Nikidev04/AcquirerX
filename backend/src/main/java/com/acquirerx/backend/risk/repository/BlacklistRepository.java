package com.acquirerx.backend.risk.repository;

import com.acquirerx.backend.risk.entity.Blacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {

    List<Blacklist> findByType(String type);

    boolean existsByTypeAndValue(String type, String value);
}
