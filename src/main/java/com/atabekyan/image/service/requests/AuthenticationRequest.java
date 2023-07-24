package com.atabekyan.image.service.requests;

public record AuthenticationRequest(String username, String password) {

    @Override
    public String toString() {
        return "AuthenticationRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
