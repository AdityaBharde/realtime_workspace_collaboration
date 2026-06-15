package com.aditya.realtime_workspace_collaboration_app.service;

import com.aditya.realtime_workspace_collaboration_app.dto.auth.AuthResponse;
import com.aditya.realtime_workspace_collaboration_app.dto.auth.LoginRequest;
import com.aditya.realtime_workspace_collaboration_app.dto.auth.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
