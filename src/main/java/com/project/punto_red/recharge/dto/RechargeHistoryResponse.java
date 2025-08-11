package com.project.punto_red.recharge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class RechargeHistoryResponse {

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "cell_phone")
    private String cellPhone;

    @JsonProperty(value = "value")
    private Double value;

    @JsonProperty(value = "supplier_id")
    private String supplierId;

    @JsonProperty(value = "created_at")
    private LocalDateTime created_at;

    public RechargeHistoryResponse(String message, String cellPhone, Double value, String supplierId, LocalDateTime created_at) {
        this.message = message;
        this.cellPhone = cellPhone;
        this.value = value;
        this.supplierId = supplierId;
        this.created_at = created_at;
    }
}
