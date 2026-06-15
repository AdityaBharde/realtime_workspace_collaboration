package com.aditya.realtime_workspace_collaboration_app.service.impl;

import com.aditya.realtime_workspace_collaboration_app.dto.auth.AuthResponse;
import com.aditya.realtime_workspace_collaboration_app.dto.auth.LoginRequest;
import com.aditya.realtime_workspace_collaboration_app.dto.auth.RegisterRequest;
import com.aditya.realtime_workspace_collaboration_app.enitity.User;
import com.aditya.realtime_workspace_collaboration_app.enums.Role;
import com.aditya.realtime_workspace_collaboration_app.repository.UserRepository;
import com.aditya.realtime_workspace_collaboration_app.security.JwtService;
import com.aditya.realtime_workspace_collaboration_app.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }
        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .name(request.name())
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken, user.getEmail(), user.getRole().name());
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        var jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken, user.getEmail(), user.getRole().name());
    }
}
