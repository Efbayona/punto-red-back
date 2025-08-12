package com.project.punto_red.operators.service.impl;

import com.project.punto_red.common.util.TokenStorage;
import com.project.punto_red.operators.service.OperatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
@Slf4j
public class OperatorServiceImpl implements OperatorService {

    @Value("${settings.request.url}")
    private String baseUrl;
    private final TokenStorage tokenStorage;

    public OperatorServiceImpl(TokenStorage tokenStorage) {
        this.tokenStorage = tokenStorage;
    }

    @Override
    public String getOperators() {
        try {
            String token = tokenStorage.getToken();

            if (token == null || token.isEmpty()) {
                log.warn("No se encontró un token válido en TokenStorage");
                return null;
            }

            String operatorsUrl = baseUrl + "/getSuppliers";

            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(operatorsUrl))
                    .timeout(Duration.ofSeconds(10))
                    .header("Content-Type", "application/json")
                    .header("Authorization", token)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            }

        } catch (Exception e) {
            log.error("Error al obtener operadores", e);
        }

        return null;
    }
}
