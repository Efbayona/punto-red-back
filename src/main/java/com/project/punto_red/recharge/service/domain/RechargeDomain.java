package com.project.punto_red.recharge.service.domain;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class RechargeDomain {

    private static final BigDecimal MIN_TRANSACTION_VALUE = new BigDecimal(1000);
    private static final BigDecimal MAX_TRANSACTION_VALUE = new BigDecimal(100000);

    private final String supplierId;

    private final String cellPhone;
    private final String operator;

    private final BigDecimal value;

    private RechargeDomain(String supplierId, String cellPhone, String operator, BigDecimal value) {
        this.supplierId = supplierId;
        this.cellPhone = cellPhone;
        this.operator = operator;
        this.value = value;
    }

    public static RechargeDomain create(String supplierId, String cellPhone, String operator,BigDecimal value){
        validateValue(value);
        validatePhoneNumber(cellPhone);
        return new RechargeDomain(supplierId, cellPhone, operator, value);
    }

    private static void validateValue(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Transaction value cannot be null ");
        }

        if (value.compareTo(MIN_TRANSACTION_VALUE) < 0) {
            throw new IllegalArgumentException("Transaction value must be at least " + MIN_TRANSACTION_VALUE);
        }
        if (value.compareTo(MAX_TRANSACTION_VALUE) > 0) {
            throw new IllegalArgumentException("Transaction value must not exceed " + MAX_TRANSACTION_VALUE);
        }
    }

    private static void validatePhoneNumber(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        if (phone.length() != 10) {
            throw new IllegalArgumentException("Phone number must have exactly 10 digits");
        }
        if (!phone.startsWith("3")) {
            throw new IllegalArgumentException("Phone number must start with '3'");
        }
        if (!phone.matches("\\d+")) {
            throw new IllegalArgumentException("Phone number must contain only digits");
        }
    }

}
