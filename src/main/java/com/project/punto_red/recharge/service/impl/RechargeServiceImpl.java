package com.project.punto_red.recharge.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.project.punto_red.common.exception.service.ServerErrorException;
import com.project.punto_red.common.util.TokenStorage;
import com.project.punto_red.recharge.dto.RechargeHistoryResponse;
import com.project.punto_red.recharge.dto.RechargeResponse;
import com.project.punto_red.recharge.entity.Recharge;
import com.project.punto_red.recharge.repository.RechargeRepository;
import com.project.punto_red.recharge.service.RechargeService;
import com.project.punto_red.recharge.service.domain.RechargeDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.UUID;

@Service
@Slf4j
public class RechargeServiceImpl implements RechargeService {

    @Value("${settings.request.url}")
    private String baseUrl;

    private final TokenStorage tokenStorage;
    private final RechargeRepository rechargeRepository;
    private final HttpClient httpClient;

    public RechargeServiceImpl(TokenStorage tokenStorage, RechargeRepository rechargeRepository, HttpClient httpClient) {
        this.tokenStorage = tokenStorage;
        this.rechargeRepository = rechargeRepository;
        this.httpClient = httpClient;
    }

    @Override
    public RechargeResponse recharge(RechargeDomain request) {
        Gson gson = new Gson();

        try {
            String jsonPayload = "{"
                    + "\"supplierId\": \"" + request.getSupplierId() + "\","
                    + "\"cellPhone\": \"" + request.getCellPhone() + "\","
                    + "\"value\": " + request.getValue()
                    + "}";

            String operatorsUrl = baseUrl + "/buy";

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(operatorsUrl))
                    .timeout(Duration.ofSeconds(10))
                    .header("Content-Type", "application/json")
                    .header("Authorization", tokenStorage.getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();

                JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);

                String message = jsonObject.get("message").getAsString();
                UUID transactionalID = UUID.fromString(jsonObject.get("transactionalID").getAsString());
                String cellPhone = jsonObject.get("cellPhone").getAsString();
                Double value = jsonObject.get("value").getAsDouble();

                return RechargeResponse.create(
                        rechargeRepository.save(Recharge.create(
                                message,
                                transactionalID,
                                cellPhone,
                                value,
                                request.getSupplierId(),
                                request.getOperator()))
                );
            }

        } catch (Exception e) {
            log.error("Error al obtener operadores", e);
        }

        throw new ServerErrorException("No es posible recargar en estos momentos.");
    }

    @Override
    public Page<RechargeHistoryResponse> getRechargeRequests(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return rechargeRepository.getRechargeRequests(pageable);
    }
}
