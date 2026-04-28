package com.acquirerx.backend.merchant.entity;

import com.acquirerx.backend.common.enums.Status;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "merchant_kyc")
public class MerchantKYC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kycID;

    private String documentType;

    // Ensure this name matches exactly what we use in the Service
    private String documentReference;

    // The bank needs to know WHEN it was verified
    private LocalDateTime verifiedDate;

    @Enumerated(EnumType.STRING)
    private Status verificationStatus;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    // Getters
    public Long getKycID() {
        return kycID;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getDocumentReference() {
        return documentReference;
    }

    public LocalDateTime getVerifiedDate() {
        return verifiedDate;
    }

    public Status getVerificationStatus() {
        return verificationStatus;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    // Setters
    public void setKycID(Long kycID) {
        this.kycID = kycID;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public void setDocumentReference(String documentReference) {
        this.documentReference = documentReference;
    }

    public void setVerifiedDate(LocalDateTime verifiedDate) {
        this.verifiedDate = verifiedDate;
    }

    public void setVerificationStatus(Status verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    // Builder
    public static MerchantKYCBuilder builder() {
        return new MerchantKYCBuilder();
    }

    public static class MerchantKYCBuilder {
        private Long kycID;
        private String documentType;
        private String documentReference;
        private LocalDateTime verifiedDate;
        private Status verificationStatus;
        private Merchant merchant;

        public MerchantKYCBuilder kycID(Long kycID) {
            this.kycID = kycID;
            return this;
        }

        public MerchantKYCBuilder documentType(String documentType) {
            this.documentType = documentType;
            return this;
        }

        public MerchantKYCBuilder documentReference(String documentReference) {
            this.documentReference = documentReference;
            return this;
        }

        public MerchantKYCBuilder verifiedDate(LocalDateTime verifiedDate) {
            this.verifiedDate = verifiedDate;
            return this;
        }

        public MerchantKYCBuilder verificationStatus(Status verificationStatus) {
            this.verificationStatus = verificationStatus;
            return this;
        }

        public MerchantKYCBuilder merchant(Merchant merchant) {
            this.merchant = merchant;
            return this;
        }

        public MerchantKYC build() {
            MerchantKYC k = new MerchantKYC();
            k.setKycID(kycID);
            k.setDocumentType(documentType);
            k.setDocumentReference(documentReference);
            k.setVerifiedDate(verifiedDate);
            k.setVerificationStatus(verificationStatus);
            k.setMerchant(merchant);
            return k;
        }
    }
}