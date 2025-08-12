package com.project.punto_red.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserRequest {

    @JsonProperty(value = "user", required = true)
    private String user;

    @JsonProperty(value = "password",required = true)
    private String password;

    @JsonProperty(value = "confirm_password",required = true)
    private String confirmPassword;

}
