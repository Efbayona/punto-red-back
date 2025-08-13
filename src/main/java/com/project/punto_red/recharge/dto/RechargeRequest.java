package com.project.punto_red.recharge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.punto_red.common.validation.annotation.ValidatorPhone;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RechargeRequest {

    @JsonProperty(value = "supplierId", required = true)
    private String supplierId;

    @JsonProperty(value = "operator", required = true)
    private String operator;

    @ValidatorPhone
    @JsonProperty(value = "cellPhone", required = true)
    private String cellPhone;

    @JsonProperty(value = "value", required = true)
    @NotNull(message = "El valor no puede ser nulo")
    @Min(value = 1000, message = "El valor mínimo es 1.000")
    @Max(value = 100000, message = "El valor máximo es 100.000")
    private Integer value;

}
