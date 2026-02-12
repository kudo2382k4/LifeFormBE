package com.fpt.edu.lifeform.controller;

import com.fpt.edu.lifeform.dto.request.CredentialsLoginRequest;
import com.fpt.edu.lifeform.dto.response.LoginResponse;
import com.fpt.edu.lifeform.service.auth.AuthService;
import com.fpt.edu.lifeform.service.auth.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/login/credential")
    public LoginResponse credentialLogin(@RequestBody CredentialsLoginRequest credentialsLoginRequest) {
        return authService.credentialsLogin(credentialsLoginRequest);
    }

    @GetMapping("/refresh")
    public LoginResponse letRefreshToken(@RequestParam("refresh_token") String refreshToken) {
        return jwtService.letRefreshToken(refreshToken);
    }

    @GetMapping("/logout")
    public void logout(@RequestParam("refresh_token") String refreshToken) {
        authService.logout(refreshToken);
    }
}
