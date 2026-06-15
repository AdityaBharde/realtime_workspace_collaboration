package com.aditya.realtime_workspace_collaboration_app.dto.auth;

public record RegisterResponse(
        Long id,
        String username,
        String email,
        String message
) {
}