package com.project.punto_red.auth.service;

import com.project.punto_red.auth.dto.CreateUserRequest;
import com.project.punto_red.auth.dto.LoginRequest;
import com.project.punto_red.auth.dto.LoginResponse;
import org.springframework.http.HttpStatus;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    void registerUser(CreateUserRequest request);
}
