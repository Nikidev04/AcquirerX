package com.acquirerx.backend.dispute.repository;

import com.acquirerx.backend.dispute.entity.DisputeDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing DisputeDocument entities (evidence files) in the database.
 * Extends JpaRepository to automatically provide standard database commands (save, delete, findById).
 */
@Repository // Registers this interface as a Spring Bean for database operations
public interface DisputeDocumentRepository extends JpaRepository<DisputeDocument, Integer> {

    // -------------------------------------------------------------------------
    // CUSTOM QUERY METHODS
    // -------------------------------------------------------------------------

    /**
     * Retrieves all supporting documents attached to a specific dispute case.
     * This uses Spring Data JPA's property expression feature. It navigates through the
     * 'disputeCase' object inside DisputeDocument to filter by its 'caseId'.
     *
     * @param caseId The unique identifier of the parent DisputeCase.
     * @return A list of DisputeDocument records containing URIs to the evidence files.
     */
    List<DisputeDocument> findByDisputeCase_CaseId(Integer caseId);

    /**
     * Optional: Retrieves all documents of a specific type across all cases
     * (e.g., finding all "Receipt" documents).
     *
     * @param docType The type of document to search for.
     * @return A list of matching DisputeDocument records.
     */
    List<DisputeDocument> findByDocType(String docType);
}