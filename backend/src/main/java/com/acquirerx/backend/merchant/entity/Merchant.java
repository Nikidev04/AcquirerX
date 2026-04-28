package com.acquirerx.backend.merchant.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import com.acquirerx.backend.common.enums.RiskLevel;
import com.acquirerx.backend.common.enums.Status;
import com.acquirerx.backend.store.entity.Store;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "merchants")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long merchantID;

    @Column(nullable = false)
    private String legalName;

    private String doingBusinessAs;

    @Column(length = 4)
    private String mcc;

    private String contactInfo;

    @Enumerated(EnumType.STRING)
    private RiskLevel riskLevel;

    @Enumerated(EnumType.STRING)
    private Status status;

    // --- RELATIONSHIPS ---

    // 1. MerchantKYC: exposed via GET /api/merchants/{id}/kyc
    @JsonIgnore
    @OneToOne(mappedBy = "merchant", cascade = CascadeType.ALL, orphanRemoval = true)
    private MerchantKYC merchantKYC;

    // 2. SettlementProfile: exposed via GET /api/merchants/{id}/settlement
    @JsonIgnore
    @OneToOne(mappedBy = "merchant", cascade = CascadeType.ALL, orphanRemoval = true)
    private SettlementProfile settlementProfile;

    // 3. PricingModel: exposed via GET /api/merchants/{id}/pricing
    @JsonIgnore
    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PricingModel> pricingModels;

    // 4. Store: exposed via GET /api/merchants/{id}/stores
    @JsonIgnore
    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Store> stores;

    // --- AUDITING ---

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // Getters
    public Long getMerchantID() {
        return merchantID;
    }

    public String getLegalName() {
        return legalName;
    }

    public String getDoingBusinessAs() {
        return doingBusinessAs;
    }

    public String getMcc() {
        return mcc;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public Status getStatus() {
        return status;
    }

    public MerchantKYC getMerchantKYC() {
        return merchantKYC;
    }

    public SettlementProfile getSettlementProfile() {
        return settlementProfile;
    }

    public List<PricingModel> getPricingModels() {
        return pricingModels;
    }

    public List<Store> getStores() {
        return stores;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setMerchantID(Long merchantID) {
        this.merchantID = merchantID;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public void setDoingBusinessAs(String doingBusinessAs) {
        this.doingBusinessAs = doingBusinessAs;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setMerchantKYC(MerchantKYC merchantKYC) {
        this.merchantKYC = merchantKYC;
    }

    public void setSettlementProfile(SettlementProfile settlementProfile) {
        this.settlementProfile = settlementProfile;
    }

    public void setPricingModels(List<PricingModel> pricingModels) {
        this.pricingModels = pricingModels;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Builder
    public static MerchantBuilder builder() {
        return new MerchantBuilder();
    }

    public static class MerchantBuilder {
        private Long merchantID;
        private String legalName;
        private String doingBusinessAs;
        private String mcc;
        private String contactInfo;
        private RiskLevel riskLevel;
        private Status status;
        private MerchantKYC merchantKYC;
        private SettlementProfile settlementProfile;
        private List<PricingModel> pricingModels;
        private List<Store> stores;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public MerchantBuilder merchantID(Long merchantID) {
            this.merchantID = merchantID;
            return this;
        }

        public MerchantBuilder legalName(String legalName) {
            this.legalName = legalName;
            return this;
        }

        public MerchantBuilder doingBusinessAs(String doingBusinessAs) {
            this.doingBusinessAs = doingBusinessAs;
            return this;
        }

        public MerchantBuilder mcc(String mcc) {
            this.mcc = mcc;
            return this;
        }

        public MerchantBuilder contactInfo(String contactInfo) {
            this.contactInfo = contactInfo;
            return this;
        }

        public MerchantBuilder riskLevel(RiskLevel riskLevel) {
            this.riskLevel = riskLevel;
            return this;
        }

        public MerchantBuilder status(Status status) {
            this.status = status;
            return this;
        }

        public MerchantBuilder merchantKYC(MerchantKYC merchantKYC) {
            this.merchantKYC = merchantKYC;
            return this;
        }

        public MerchantBuilder settlementProfile(SettlementProfile settlementProfile) {
            this.settlementProfile = settlementProfile;
            return this;
        }

        public MerchantBuilder pricingModels(List<PricingModel> pricingModels) {
            this.pricingModels = pricingModels;
            return this;
        }

        public MerchantBuilder stores(List<Store> stores) {
            this.stores = stores;
            return this;
        }

        public MerchantBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public MerchantBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Merchant build() {
            Merchant m = new Merchant();
            m.setMerchantID(merchantID);
            m.setLegalName(legalName);
            m.setDoingBusinessAs(doingBusinessAs);
            m.setMcc(mcc);
            m.setContactInfo(contactInfo);
            m.setRiskLevel(riskLevel);
            m.setStatus(status);
            m.setMerchantKYC(merchantKYC);
            m.setSettlementProfile(settlementProfile);
            m.setPricingModels(pricingModels);
            m.setStores(stores);
            m.setCreatedAt(createdAt);
            m.setUpdatedAt(updatedAt);
            return m;
        }
    }
}