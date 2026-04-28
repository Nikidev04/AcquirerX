package com.acquirerx.backend.terminal.repository;

import com.acquirerx.backend.terminal.entity.ParamProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParamProfileRepository extends JpaRepository<ParamProfile, Long> {
    List<ParamProfile> findByStatus(String status);
}
