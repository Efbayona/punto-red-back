package com.project.punto_red.recharge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class RechargeRequest implements Serializable {

    @JsonProperty(value = "supplierId", required = true)
    private String supplierId;

    @JsonProperty(value = "cellPhone", required = true)
    private String cellPhone;

    @JsonProperty(value = "value", required = true)
    private String value;

}
