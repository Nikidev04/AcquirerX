package com.acquirerx.backend.merchant.dto;

import com.acquirerx.backend.common.enums.Status;

public class KYCRequestDTO {
    private String documentType; // ID_PROOF, TAX_ID, BUSINESS_LICENSE
    private String documentReference; // e.g., "doc_001.pdf"
    private Status verificationStatus;

    // Getters
    public String getDocumentType() {
        return documentType;
    }

    public String getDocumentReference() {
        return documentReference;
    }

    public Status getVerificationStatus() {
        return verificationStatus;
    }

    // Setters
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public void setDocumentReference(String documentReference) {
        this.documentReference = documentReference;
    }

    public void setVerificationStatus(Status verificationStatus) {
        this.verificationStatus = verificationStatus;
    }
}