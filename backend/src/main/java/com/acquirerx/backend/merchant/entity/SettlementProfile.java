package com.acquirerx.backend.merchant.entity;

import com.acquirerx.backend.common.enums.SettlementCycle;
import jakarta.persistence.*;

@Entity
@Table(name = "settlement_profiles")
public class SettlementProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileID;

    @Enumerated(EnumType.STRING)
    private SettlementCycle settlementCycle; // DAILY, T_PLUS_1, etc.

    private String bankAccountNumber;

    private String swiftCode;

    @OneToOne
    @JoinColumn(name = "merchant_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Merchant merchant;

    // Getters
    public Long getProfileID() {
        return profileID;
    }

    public SettlementCycle getSettlementCycle() {
        return settlementCycle;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    // Setters
    public void setProfileID(Long profileID) {
        this.profileID = profileID;
    }

    public void setSettlementCycle(SettlementCycle settlementCycle) {
        this.settlementCycle = settlementCycle;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    // Builder
    public static SettlementProfileBuilder builder() {
        return new SettlementProfileBuilder();
    }

    public static class SettlementProfileBuilder {
        private Long profileID;
        private SettlementCycle settlementCycle;
        private String bankAccountNumber;
        private String swiftCode;
        private Merchant merchant;

        public SettlementProfileBuilder profileID(Long profileID) {
            this.profileID = profileID;
            return this;
        }

        public SettlementProfileBuilder settlementCycle(SettlementCycle settlementCycle) {
            this.settlementCycle = settlementCycle;
            return this;
        }

        public SettlementProfileBuilder bankAccountNumber(String bankAccountNumber) {
            this.bankAccountNumber = bankAccountNumber;
            return this;
        }

        public SettlementProfileBuilder swiftCode(String swiftCode) {
            this.swiftCode = swiftCode;
            return this;
        }

        public SettlementProfileBuilder merchant(Merchant merchant) {
            this.merchant = merchant;
            return this;
        }

        public SettlementProfile build() {
            SettlementProfile p = new SettlementProfile();
            p.setProfileID(profileID);
            p.setSettlementCycle(settlementCycle);
            p.setBankAccountNumber(bankAccountNumber);
            p.setSwiftCode(swiftCode);
            p.setMerchant(merchant);
            return p;
        }
    }
}