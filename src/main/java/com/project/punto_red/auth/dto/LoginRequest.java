package com.project.punto_red.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class LoginRequest implements Serializable {

    @JsonProperty(value = "user", required = true)
    private String user;

    @JsonProperty(value = "password",required = true)
    private String password;
}
