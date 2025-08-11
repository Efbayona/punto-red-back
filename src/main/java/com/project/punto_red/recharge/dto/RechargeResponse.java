package com.project.punto_red.recharge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class RechargeResponse implements Serializable {

    @JsonProperty(value = "cellPhone", required = true)
    private String cellPhone;

    @JsonProperty(value = "value", required = true)
    private String value;

}
