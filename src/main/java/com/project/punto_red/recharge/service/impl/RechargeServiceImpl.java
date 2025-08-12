package com.project.punto_red.recharge.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.project.punto_red.common.util.TokenStorage;
import com.project.punto_red.recharge.dto.RechargeHistoryResponse;
import com.project.punto_red.recharge.dto.RechargeRequest;
import com.project.punto_red.recharge.dto.RechargeResponse;
import com.project.punto_red.recharge.entity.Recharge;
import com.project.punto_red.recharge.repository.RechargeRepository;
import com.project.punto_red.recharge.service.RechargeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class RechargeServiceImpl implements RechargeService {

    @Value("${settings.request.url}")
    private String baseUrl;

    private final TokenStorage tokenStorage;
    private final RechargeRepository rechargeRepository;

    public RechargeServiceImpl(TokenStorage tokenStorage, RechargeRepository rechargeRepository) {
        this.tokenStorage = tokenStorage;
        this.rechargeRepository = rechargeRepository;
    }

    @Override
    public RechargeResponse recharge(RechargeRequest request) {

        Gson gson = new Gson();

        try {
            String token = tokenStorage.getToken();

            if (token == null || token.isEmpty()) {
                log.warn("No se encontró un token válido en TokenStorage");
                return null;
            }

            String json = gson.toJson(request);

            String operatorsUrl = baseUrl + "/buy";

            HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(operatorsUrl))
                    .timeout(Duration.ofSeconds(10))
                    .header("Content-Type", "application/json")
                    .header("Authorization", token).POST(HttpRequest.BodyPublishers.ofString(json)).build();

            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();

                JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);

                String message = jsonObject.get("message").getAsString();
                UUID transactionalID = UUID.fromString(jsonObject.get("transactionalID").getAsString());
                String cellPhone = jsonObject.get("cellPhone").getAsString();
                Double value = jsonObject.get("value").getAsDouble();

                return RechargeResponse.create(rechargeRepository.save(Recharge.create(message, transactionalID, cellPhone, value, request.getSupplierId())));

            }

        } catch (Exception e) {
            log.error("Error al obtener operadores", e);
        }

        return null;
    }

    @Override
    public List<RechargeHistoryResponse> getRechargeRequests() {
        return rechargeRepository.getRechargeRequests();
    }
}
