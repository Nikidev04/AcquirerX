package com.acquirerx.backend.dispute.repository;

import com.acquirerx.backend.dispute.entity.DisputeCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing DisputeCase entities in the database.
 * * By extending JpaRepository, Spring Data JPA automatically provides the implementation
 * for standard CRUD operations (Create, Read, Update, Delete) behind the scenes.
 * We do not need to write basic SQL queries like 'INSERT INTO' or 'SELECT * FROM'.
 */
@Repository // Marks this as a Spring-managed Data Access Object (DAO)
public interface DisputeCaseRepository extends JpaRepository<DisputeCase, Integer> {

    // -------------------------------------------------------------------------
    // CUSTOM QUERY METHODS
    // Spring Boot magically writes the SQL for these simply by reading the method names!
    // -------------------------------------------------------------------------

    /**
     * Finds all dispute cases linked to a specific transaction.
     * This is critical to ensure a merchant doesn't accidentally open two disputes
     * for the exact same transaction.
     * * @param txnId The ID of the original transaction.
     * @return A list of matching DisputeCase records.
     */
    List<DisputeCase> findByTxnId(Integer txnId);

    /**
     * Finds all dispute cases that have a specific status (e.g., "Open" or "Closed").
     * This will be used heavily by the frontend dashboard to show active work queues.
     * * @param status The current status of the case.
     * @return A list of matching DisputeCase records.
     */
    List<DisputeCase> findByStatus(String status);

    /**
     * Finds all dispute cases currently sitting in a specific lifecycle stage
     * (e.g., "Retrieval", "Chargeback", "Representment", "Arbitration").
     * * @param stage The current stage in the dispute lifecycle.
     * @return A list of matching DisputeCase records.
     */
    List<DisputeCase> findByStage(String stage);
}