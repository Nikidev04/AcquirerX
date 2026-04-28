package com.acquirerx.backend.dispute.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Entity class representing an action or event in the lifecycle of a Dispute Case.
 * This acts as an immutable audit trail, recording every stage change, document request,
 * or resolution step taken by a system user (Actor).
 */
@Entity // Tells Spring/Hibernate to map this class to a database table
@Table(name = "dispute_actions") // Explicitly names the table in the H2 database
public class DisputeAction {

    // -------------------------------------------------------------------------
    // PRIMARY KEY
    // -------------------------------------------------------------------------

    @Id // Marks this field as the Primary Key for this specific table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tells H2 to auto-increment this ID
    @Column(name = "action_id")
    private Integer actionId; // Unique identifier for the audit log entry

    // -------------------------------------------------------------------------
    // RELATIONSHIPS (FOREIGN KEY)
    // -------------------------------------------------------------------------

    /**
     * Many-To-One relationship with DisputeCase.
     * Many actions (a full history) belong to exactly ONE dispute case.
     * @JoinColumn creates the actual foreign key column ("case_id") in the database table.
     * @JsonIgnore prevents an "infinite recursion" error when converting data to JSON for APIs.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", nullable = false)
    @JsonIgnore
    private DisputeCase disputeCase;

    // -------------------------------------------------------------------------
    // CORE ATTRIBUTES
    // -------------------------------------------------------------------------

    @Column(name = "action_type", nullable = false)
    private String actionType; // Identifies what happened (e.g., "RequestDocs", "Submit", "Accept", "Reject", "WriteOff")

    @Column(name = "actor_id", nullable = false)
    private Integer actorId; // The ID of the User (e.g., Disputes Analyst) who performed the action

    @Column(name = "action_date", nullable = false)
    private LocalDateTime actionDate; // Timestamp recording exactly when the action occurred

    @Column(name = "notes", length = 1000) // length = 1000 allows for longer text explanations
    private String notes; // Optional context, reasoning, or comments provided by the actor

    public DisputeAction() {
    }

    public DisputeAction(Integer actionId, DisputeCase disputeCase, String actionType, Integer actorId, LocalDateTime actionDate, String notes) {
        this.actionId = actionId;
        this.disputeCase = disputeCase;
        this.actionType = actionType;
        this.actorId = actorId;
        this.actionDate = actionDate;
        this.notes = notes;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public DisputeCase getDisputeCase() {
        return disputeCase;
    }

    public void setDisputeCase(DisputeCase disputeCase) {
        this.disputeCase = disputeCase;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public LocalDateTime getActionDate() {
        return actionDate;
    }

    public void setActionDate(LocalDateTime actionDate) {
        this.actionDate = actionDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}