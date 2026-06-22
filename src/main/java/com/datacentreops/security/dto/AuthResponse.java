package com.datacentreops.security.dto;

public record AuthResponse(
        String token,
        String tokenType,
        Long userId,
        String email,
        String role) {

    public static AuthResponse bearer(String token, Long userId, String email, String role) {
        return new AuthResponse(token, "Bearer", userId, email, role);
    }
}
