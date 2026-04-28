package com.acquirerx.backend.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SettlementCycle {

    DAILY("DAILY"),
    T_PLUS_1("T+1"),
    T_PLUS_2("T+2"),
    WEEKLY("WEEKLY");

    private final String label;

    SettlementCycle(String label) {
        this.label = label;
    }

    // Serialize as "T+1" instead of "T_PLUS_1" in JSON responses
    @JsonValue
    public String getLabel() {
        return label;
    }

    // Accept both "T+1" and "T_PLUS_1" in JSON requests
    @JsonCreator
    public static SettlementCycle fromValue(String value) {
        if (value == null) return null;
        for (SettlementCycle cycle : values()) {
            if (cycle.label.equalsIgnoreCase(value) || cycle.name().equalsIgnoreCase(value)) {
                return cycle;
            }
        }
        throw new IllegalArgumentException(
            "Invalid settlementCycle: \"" + value + "\". Accepted values: DAILY, T+1, T+2, WEEKLY"
        );
    }
}
