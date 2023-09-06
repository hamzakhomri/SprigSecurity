package com.example.security.dto;

public class AuthenticationResponse {


    private String token;

    public AuthenticationResponse()
    {

    }

    public AuthenticationResponse(String token) {
        super();
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
