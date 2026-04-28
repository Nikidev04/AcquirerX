package com.acquirerx.backend.dispute.controller;

import com.acquirerx.backend.dispute.dto.DisputeActionDTO;
import com.acquirerx.backend.dispute.dto.DisputeCaseDTO;
import com.acquirerx.backend.dispute.dto.DisputeDocumentDTO;
import com.acquirerx.backend.dispute.entity.DisputeDocument;
import com.acquirerx.backend.dispute.service.DisputeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Dispute Cases.
 * This class acts as the API endpoint (the "Waiter") that the frontend or external systems
 * will call to interact with the AcquirerX dispute module.
 */
@RestController // Tells Spring this class handles web requests and returns JSON responses
@RequestMapping("/api/disputes") // Sets the base URL for every endpoint in this file
public class DisputeController {

    // -------------------------------------------------------------------------
    // DEPENDENCIES
    // -------------------------------------------------------------------------

    // The Controller only talks to the Service (The Chef), NEVER directly to the database.
    private final DisputeService disputeService;

    public DisputeController(DisputeService disputeService) {
        this.disputeService = disputeService;
    }

    // -------------------------------------------------------------------------
    // ENDPOINTS (The Menu)
    // -------------------------------------------------------------------------

    /**
     * POST /api/disputes
     * Opens a brand new dispute case.
     * * @param dto The incoming dispute details (validated by @Valid).
     * @param actorId The ID of the user performing the action (passed via HTTP Header).
     * @return The created DisputeCaseDTO with a 201 Created status.
     */
    @PostMapping
    public ResponseEntity<DisputeCaseDTO> createDispute(
            @Valid @RequestBody DisputeCaseDTO dto,
            @RequestHeader(value = "X-Actor-Id", defaultValue = "1") Integer actorId) {

        DisputeCaseDTO createdCase = disputeService.createDisputeCase(dto, actorId);
        return new ResponseEntity<>(createdCase, HttpStatus.CREATED);
    }

    /**
     * GET /api/disputes
     * Fetches a list of all active disputes.
     * * @return A list of DisputeCaseDTOs with a 200 OK status.
     */
    @GetMapping
    public ResponseEntity<List<DisputeCaseDTO>> getAllDisputes() {
        List<DisputeCaseDTO> disputes = disputeService.getAllDisputes();
        return ResponseEntity.ok(disputes);
    }

    /**
     * GET /api/disputes/{caseId}
     * Fetches the details of one specific dispute case.
     * * @param caseId The unique ID of the case, extracted from the URL.
     * @return The DisputeCaseDTO with a 200 OK status.
     */
    @GetMapping("/{caseId}")
    public ResponseEntity<DisputeCaseDTO> getDisputeById(@PathVariable("caseId") Integer caseId) {
        DisputeCaseDTO dispute = disputeService.getDisputeById(caseId);
        return ResponseEntity.ok(dispute);
    }

    /**
     * PUT /api/disputes/{caseId}/stage
     * Advances the lifecycle stage of a dispute (e.g., from Chargeback to Representment).
     * * @param caseId The ID of the case to update.
     * @param newStage The new stage name sent in the URL query parameter.
     * @param notes Optional reasoning for the stage change.
     * @param actorId The ID of the user performing the action.
     * @return The updated DisputeCaseDTO.
     */
    @PutMapping("/{caseId}/stage")
    public ResponseEntity<DisputeCaseDTO> advanceStage(
            @PathVariable("caseId") Integer caseId,
            @RequestParam("newStage") String newStage,
            @RequestParam(value = "notes", required = false, defaultValue = "Stage updated via API") String notes,
            @RequestHeader(value = "X-Actor-Id", defaultValue = "1") Integer actorId) {

        DisputeCaseDTO updatedCase = disputeService.advanceDisputeStage(caseId, newStage, actorId, notes);
        return ResponseEntity.ok(updatedCase);
    }

    /**
     * POST /api/disputes/documents
     * Uploads and attaches a piece of evidence (document) to a dispute case.
     * * @param dto The document details including the caseId it belongs to.
     * @param actorId The ID of the user uploading the document.
     * @return The saved DisputeDocumentDTO.
     */
    @PostMapping("/documents")
    public ResponseEntity<DisputeDocumentDTO> addDocument(
            @Valid @RequestBody DisputeDocumentDTO dto,
            @RequestHeader(value = "X-Actor-Id", defaultValue = "1") Integer actorId) {

        DisputeDocumentDTO savedDoc = disputeService.addDocumentToDispute(dto, actorId);
        return new ResponseEntity<>(savedDoc, HttpStatus.CREATED);
    }

    /**
     * PUT /api/disputes/{caseId}/close
     * Closes a dispute case, setting its status to "Closed" and logging the action.
     * * @param caseId  The ID of the case to close.
     * @param actorId The ID of the user closing the case (passed via HTTP Header).
     * @param notes   Optional notes explaining why the case is being closed.
     * @return The updated DisputeCaseDTO with status "Closed".
     */
    @PutMapping("/{caseId}/close")
    public ResponseEntity<DisputeCaseDTO> closeDispute(
            @PathVariable("caseId") Integer caseId,
            @RequestHeader(value = "X-Actor-Id", defaultValue = "1") Integer actorId,
            @RequestParam(value = "notes", required = false, defaultValue = "Case closed") String notes) {

        DisputeCaseDTO closedCase = disputeService.closeDispute(caseId, actorId, notes);
        return ResponseEntity.ok(closedCase);
    }

    /**
     * GET /api/disputes/{caseId}/documents
     * Retrieves all supporting documents (evidence) attached to a specific dispute case.
     * * @param caseId The ID of the case whose documents are being fetched.
     * @return A list of DisputeDocument entities with a 200 OK status.
     */
    @GetMapping("/{caseId}/documents")
    public ResponseEntity<List<DisputeDocument>> getDocumentsByCase(@PathVariable("caseId") Integer caseId) {
        List<DisputeDocument> documents = disputeService.getDocumentsByCase(caseId);
        return ResponseEntity.ok(documents);
    }

    /**
     * GET /api/disputes/{caseId}/actions
     * Retrieves the complete audit trail (all logged actions) for a specific dispute case.
     * * @param caseId The ID of the case whose action history is being fetched.
     * @return A list of DisputeActionDTOs with a 200 OK status.
     */
    @GetMapping("/{caseId}/actions")
    public ResponseEntity<List<DisputeActionDTO>> getActionsByCase(@PathVariable("caseId") Integer caseId) {
        List<DisputeActionDTO> actions = disputeService.getActionsByCase(caseId);
        return ResponseEntity.ok(actions);
    }
}