package com.project.punto_red.recharge;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
@Slf4j
public class RechargeService {

    private final String bearerToken = null;

    private final Gson gson = new Gson();


    private String AuthRequests(String jsonRequest, String endpoint, String method) throws IOException, InterruptedException {
        if (bearerToken == null) {
            throw new IllegalStateException("No Bearer token available. Authenticate first.");
        }

        String baseUrl = "https://us-central1-puntored-dev.cloudfunctions.net/technicalTest-developer/api";
        String url = baseUrl + endpoint;

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .header("Authorization", "Bearer " + bearerToken)
                .header("x-api-key", "Toke");

        if ("POST".equalsIgnoreCase(method)) {
            requestBuilder.header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest));
        } else if ("GET".equalsIgnoreCase(method)) {
            requestBuilder.header("Accept", "application/json")
                    .GET();
        } else {
            throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }

        HttpResponse<String> response = client.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
