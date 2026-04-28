package com.acquirerx.backend.dispute.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Entity class representing a supporting document for a Dispute Case.
 * Instead of storing heavy files (like PDFs or images) directly in the database,
 * this table stores a URI (link) pointing to where the file is securely saved.
 */
@Entity // Tells Spring/Hibernate to map this class to a database table
@Table(name = "dispute_documents") // Explicitly names the table in the H2 database
public class DisputeDocument {

    // -------------------------------------------------------------------------
    // PRIMARY KEY
    // -------------------------------------------------------------------------

    @Id // Marks this field as the Primary Key for this specific table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tells H2 to auto-increment this ID
    @Column(name = "doc_id")
    private Integer docId; // Unique identifier for the document [cite: 237]

    // -------------------------------------------------------------------------
    // RELATIONSHIPS (FOREIGN KEY)
    // -------------------------------------------------------------------------

    /**
     * Many-To-One relationship with DisputeCase.
     * Many documents can belong to exactly ONE dispute case[cite: 238].
     * * @JoinColumn creates the actual foreign key column ("case_id") in the database table.
     * @JsonIgnore prevents an "infinite recursion" error. If we try to return this document
     * via an API, it won't try to fetch the Case, which fetches the Document, which fetches the Case...
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", nullable = false)
    @JsonIgnore
    private DisputeCase disputeCase;

    // -------------------------------------------------------------------------
    // CORE ATTRIBUTES
    // -------------------------------------------------------------------------

    @Column(name = "doc_type", nullable = false)
    private String docType; // Categorizes the document (e.g., "Receipt", "Invoice", "Proof")

    @Column(name = "uri", nullable = false, length = 1000)
    private String uri; // The path or URL where the actual file is stored (e.g., S3 bucket link) [cite: 240]

    @Column(name = "uploaded_date", nullable = false)
    private LocalDateTime uploadedDate; // Timestamp of when the document was added [cite: 241]

    public DisputeDocument() {
    }

    public DisputeDocument(Integer docId, DisputeCase disputeCase, String docType, String uri, LocalDateTime uploadedDate) {
        this.docId = docId;
        this.disputeCase = disputeCase;
        this.docType = docType;
        this.uri = uri;
        this.uploadedDate = uploadedDate;
    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public DisputeCase getDisputeCase() {
        return disputeCase;
    }

    public void setDisputeCase(DisputeCase disputeCase) {
        this.disputeCase = disputeCase;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public LocalDateTime getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(LocalDateTime uploadedDate) {
        this.uploadedDate = uploadedDate;
    }
}