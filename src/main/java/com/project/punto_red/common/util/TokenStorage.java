package com.project.punto_red.common.util;

import org.springframework.stereotype.Service;

@Service
public class TokenStorage {

    private String bearerToken;

    public void setToken(String token) {
        this.bearerToken = token;
    }

    public String getToken() {
        return bearerToken;
    }

}
