package com.acquirerx.backend.terminal.repository;

import com.acquirerx.backend.terminal.entity.TerminalHealth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TerminalHealthRepository extends JpaRepository<TerminalHealth, Long> {
    List<TerminalHealth> findByTerminalId(Long terminalId);
}
