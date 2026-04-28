package com.acquirerx.backend.merchant.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pricing_models")
public class PricingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pricingID;

    private String pricingType; // e.g., MDR, IC++, BLENDED

    private BigDecimal baseFee;

    private BigDecimal percentage;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Merchant merchant;

    // Getters
    public Long getPricingID() {
        return pricingID;
    }

    public String getPricingType() {
        return pricingType;
    }

    public BigDecimal getBaseFee() {
        return baseFee;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    // Setters
    public void setPricingID(Long pricingID) {
        this.pricingID = pricingID;
    }

    public void setPricingType(String pricingType) {
        this.pricingType = pricingType;
    }

    public void setBaseFee(BigDecimal baseFee) {
        this.baseFee = baseFee;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    // Builder
    public static PricingModelBuilder builder() {
        return new PricingModelBuilder();
    }

    public static class PricingModelBuilder {
        private Long pricingID;
        private String pricingType;
        private BigDecimal baseFee;
        private BigDecimal percentage;
        private Merchant merchant;

        public PricingModelBuilder pricingID(Long pricingID) {
            this.pricingID = pricingID;
            return this;
        }

        public PricingModelBuilder pricingType(String pricingType) {
            this.pricingType = pricingType;
            return this;
        }

        public PricingModelBuilder baseFee(BigDecimal baseFee) {
            this.baseFee = baseFee;
            return this;
        }

        public PricingModelBuilder percentage(BigDecimal percentage) {
            this.percentage = percentage;
            return this;
        }

        public PricingModelBuilder merchant(Merchant merchant) {
            this.merchant = merchant;
            return this;
        }

        public PricingModel build() {
            PricingModel p = new PricingModel();
            p.setPricingID(pricingID);
            p.setPricingType(pricingType);
            p.setBaseFee(baseFee);
            p.setPercentage(percentage);
            p.setMerchant(merchant);
            return p;
        }
    }
}