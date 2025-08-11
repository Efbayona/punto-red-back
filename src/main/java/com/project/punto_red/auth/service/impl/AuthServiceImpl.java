package com.project.punto_red.auth.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.project.punto_red.auth.dto.LoginRequest;
import com.project.punto_red.auth.dto.LoginResponse;
import com.project.punto_red.auth.service.AuthService;
import com.project.punto_red.common.exception.service.AuthenticationFailedException;
import com.project.punto_red.common.util.TokenStorage;
import lombok.extern.slf4j.Slf4j;
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

    public AuthServiceImpl(TokenStorage tokenStorage) {
        this.tokenStorage = tokenStorage;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        try {
            String baseUrl = "https://us-central1-puntored-dev.cloudfunctions.net/technicalTest-developer/api";
            String loginUrl = baseUrl + "/auth";

            String jsonLogin = gson.toJson(request);

            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(loginUrl))
                    .timeout(Duration.ofSeconds(10))
                    .header("Content-Type", "application/json")
                    .header("x-api-key", "mtrQF6Q11eosqyQnkMY0JGFbGqcxVg5icvfVnX1ifIyWDvwGApJ8WUM8nHVrdSkN")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonLogin))
                    .build();

            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
                String token = jsonObject.get("token").getAsString();
                tokenStorage.setToken(token);
                return gson.fromJson(response.body(), LoginResponse.class);
            } else {
                throw new AuthenticationFailedException("Credenciales de authenticacion incorrectas");
            }

        } catch (Exception e) {
            log.error("Error en login", e);
            throw new AuthenticationFailedException("Error en login");
        }
    }
}
