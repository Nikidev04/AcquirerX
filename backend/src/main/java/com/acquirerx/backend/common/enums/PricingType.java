package com.acquirerx.backend.common.enums;

public enum PricingType {
    MDR,        // Merchant Discount Rate (Percentage-based)
    FLAT_FEE,   // Fixed amount per transaction
    INTERCHANGE // (Optional) Advanced banking fee type
}