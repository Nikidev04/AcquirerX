package com.acquirerx.backend.merchant.dto;

import com.acquirerx.backend.common.enums.SettlementCycle;

public class SettlementProfileDTO {

    private SettlementCycle settlementCycle;
    private String bankAccountNumber;
    private String swiftCode;

    public SettlementCycle getSettlementCycle() {
        return settlementCycle;
    }

    public void setSettlementCycle(SettlementCycle settlementCycle) {
        this.settlementCycle = settlementCycle;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }
}
