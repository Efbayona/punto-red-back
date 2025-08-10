package com.project.punto_red.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class LoginRequest implements Serializable {

    @NotBlank
    @JsonProperty(value = "user_name", required = true)
    private String userName;

    @NotBlank
    @JsonProperty(value = "password",required = true)
    private String password;
}
