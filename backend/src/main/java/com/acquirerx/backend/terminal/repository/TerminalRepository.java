package com.acquirerx.backend.terminal.repository;

import com.acquirerx.backend.terminal.entity.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal, Long> {
    // Spring Boot magically writes the SQL for this just based on the method name!
    Terminal findByTid(String tid);

    List<Terminal> findByStatus(String status);

    List<Terminal> findByStoreId(String storeId);
}
