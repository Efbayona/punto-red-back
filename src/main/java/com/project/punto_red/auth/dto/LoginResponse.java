package com.project.punto_red.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {

    @JsonProperty(value = "token_jwt", required = true)
    private String jwt;

    @JsonProperty(value = "type_token",required = true)
    private String typeToken;

}
