package com.project.punto_red.auth.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.project.punto_red.auth.dto.LoginRequest;
import com.project.punto_red.auth.dto.LoginResponse;
import com.project.punto_red.auth.service.AuthService;
import com.project.punto_red.common.exception.service.AuthenticationFailedException;
import com.project.punto_red.common.util.TokenStorage;
import com.project.punto_red.security.jwt.JwtTokenProvider;
import com.project.punto_red.user.entity.User;
import com.project.punto_red.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final Gson gson = new Gson();
    private final TokenStorage tokenStorage;

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(TokenStorage tokenStorage, UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.tokenStorage = tokenStorage;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.getUserByUserName(request.getUser()).orElseThrow(()-> new UsernameNotFoundException("credenciales incorrectas"));

        if (!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new UsernameNotFoundException("credenciales incorrectas");
        }

        return new LoginResponse(jwtTokenProvider.generateToken(request.getUser()));

    }
}
