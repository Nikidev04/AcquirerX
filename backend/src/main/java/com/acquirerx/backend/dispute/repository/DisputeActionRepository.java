package com.acquirerx.backend.dispute.repository;

import com.acquirerx.backend.dispute.entity.DisputeAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing DisputeAction entities (the audit trail) in the database.
 * Extends JpaRepository to automatically handle all standard database operations.
 */
@Repository // Tells Spring this is a component used for database access
public interface DisputeActionRepository extends JpaRepository<DisputeAction, Integer> {

    // -------------------------------------------------------------------------
    // CUSTOM QUERY METHODS
    // -------------------------------------------------------------------------

    /**
     * Finds all actions (the complete history log) attached to a specific dispute case.
     * * Notice the naming convention: 'findByDisputeCase_CaseId'.
     * Spring Boot looks at the 'disputeCase' object inside the DisputeAction entity,
     * then looks inside that object for the 'caseId', and writes the exact SQL JOIN
     * required to fetch the data.
     * * @param caseId The unique ID of the parent DisputeCase.
     * @return A list of DisputeAction records, representing the case timeline.
     */
    List<DisputeAction> findByDisputeCase_CaseId(Integer caseId);

    /**
     * Optional: Finds all actions performed by a specific user (actor).
     * This is useful if an admin wants to audit the work of a specific Disputes Analyst.
     * * @param actorId The ID of the user who performed the action.
     * @return A list of DisputeAction records.
     */
    List<DisputeAction> findByActorId(Integer actorId);
}