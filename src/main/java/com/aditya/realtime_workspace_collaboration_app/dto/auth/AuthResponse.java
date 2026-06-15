package com.aditya.realtime_workspace_collaboration_app.dto.auth;

public record AuthResponse(
        String token,
        String email,
        String role
) {
}
