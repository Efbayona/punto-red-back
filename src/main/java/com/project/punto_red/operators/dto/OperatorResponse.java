package com.project.punto_red.operators.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OperatorResponse {

    @JsonProperty(value = "token", required = true)
    private String token;

}
