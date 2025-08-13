package com.project.punto_red.auth.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.project.punto_red.auth.dto.CreateUserRequest;
import com.project.punto_red.auth.dto.LoginRequest;
import com.project.punto_red.auth.dto.LoginResponse;
import com.project.punto_red.auth.service.AuthService;
import com.project.punto_red.common.exception.service.AuthenticationFailedException;
import com.project.punto_red.common.exception.service.ConflictException;
import com.project.punto_red.common.exception.service.ServerErrorException;
import com.project.punto_red.common.util.TokenStorage;
import com.project.punto_red.security.jwt.JwtTokenProvider;
import com.project.punto_red.user.entity.User;
import com.project.punto_red.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Objects;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Value("${settings.request.url}")
    private String baseUrl;

    @Value("${settings.request.key}")
    private String key;

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
        User user = userRepository.getUserByUserName(request.getUser()).orElseThrow(() -> new UsernameNotFoundException("credenciales incorrectas"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UsernameNotFoundException("credenciales incorrectas");
        }

        String token = requestService();
        if (token == null) {
            throw new ServerErrorException("Estamos teniendo problemas, reintenta más tarde.");
        }
        tokenStorage.setToken(token);

        return new LoginResponse(jwtTokenProvider.generateToken(request.getUser()));

    }

    @Override
    public void registerUser(CreateUserRequest request) {

        if (userRepository.existsByUserName(request.getUserName())) {
            throw new ConflictException("Nombre de usuario ya existe");
        }

        if (!Objects.equals(request.getPassword(), request.getConfirmPassword())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        userRepository.save(User.create(request.getUserName(), passwordEncoder.encode(request.getPassword())));
    }

    public String requestService() {
        Gson gson = new Gson();

        String jsonLogin = "{ \"user\": \"user0147\", \"password\": \"#3Q34Sh0NlDS\" }";

        try {
            String loginUrl = baseUrl + "/auth";

            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(loginUrl))
                    .timeout(Duration.ofSeconds(10))
                    .header("Content-Type", "application/json")
                    .header("x-api-key", key)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonLogin))
                    .build();

            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new AuthenticationFailedException("Credenciales de autenticación incorrectas");
            }

            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            return jsonObject.get("token").getAsString();

        } catch (Exception e) {
            log.error("Error en login API Punto Red", e);
            return null;
        }
    }

}