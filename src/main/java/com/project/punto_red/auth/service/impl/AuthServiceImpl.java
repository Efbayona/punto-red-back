package com.project.punto_red.auth.service.impl;

import com.google.gson.Gson;
import com.project.punto_red.auth.dto.LoginRequest;
import com.project.punto_red.auth.dto.LoginResponse;
import com.project.punto_red.auth.service.AuthService;
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
                    .POST(HttpRequest.BodyPublishers.ofString(jsonLogin))
                    .build();

            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String body = response.body();
                log.info("Login response body: {}", body);

                return gson.fromJson(body, LoginResponse.class);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}