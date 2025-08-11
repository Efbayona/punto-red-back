package com.project.punto_red.recharge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.punto_red.recharge.entity.Recharge;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class RechargeResponse {

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "cell_phone")
    private String cellPhone;

    @JsonProperty(value = "value")
    private Double value;

    @JsonProperty(value = "supplier_id")
    private String supplierId;

    @JsonProperty(value = "transactional_id")
    private UUID transactionalId;

    @JsonProperty(value = "created_at")
    private LocalDateTime created_at;

    public RechargeResponse(String message, String cellPhone, Double value, String supplierId, UUID transactionalId, LocalDateTime created_at) {
        this.message = message;
        this.cellPhone = cellPhone;
        this.value = value;
        this.supplierId = supplierId;
        this.transactionalId = transactionalId;
        this.created_at = created_at;
    }

    public static RechargeResponse create(Recharge recharge) {
        return new RechargeResponse(
                recharge.getMessage(),
                recharge.getCellPhone(),
                recharge.getValue(),
                recharge.getSupplierId(),
                recharge.getTransactionalID(),
                recharge.getCreatedAt());
    }
}
