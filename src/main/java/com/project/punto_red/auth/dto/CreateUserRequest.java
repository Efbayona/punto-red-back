package com.project.punto_red.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CreateUserRequest {

    @JsonProperty(value = "user_name", required = true)
    private String userName;

    @JsonProperty(value = "password",required = true)
    private String password;

    @JsonProperty(value = "confirm_password",required = true)
    private String confirmPassword;

}
