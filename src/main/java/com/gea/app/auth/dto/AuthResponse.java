package com.gea.app.auth.dto;

public class AuthResponse {
    private final String accessToken;
    private final String tokenType;

    public AuthResponse(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }

    // Convenience ctor: default "Bearer"
    public AuthResponse(String accessToken) {
        this(accessToken, "Bearer");
    }

    public String getAccessToken() { return accessToken; }
    public String getTokenType() { return tokenType; }
}
