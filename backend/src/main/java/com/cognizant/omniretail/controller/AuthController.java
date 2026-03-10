package com.cognizant.omniretail.controller;

import com.cognizant.omniretail.dto.request.ForgotPasswordRequest;
import com.cognizant.omniretail.dto.request.LoginRequest;
import com.cognizant.omniretail.dto.request.ResetPasswordRequest;
import com.cognizant.omniretail.dto.response.ApiMessageResponse;
import com.cognizant.omniretail.dto.response.AuthResponse;
import com.cognizant.omniretail.service.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/logout")
    public ResponseEntity<ApiMessageResponse> logout() {
        authService.logout();
        return ResponseEntity.ok(new ApiMessageResponse("Logout successful. Please remove the token on client side."));
    }

    // ---------- Forgot Password (public) ----------
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiMessageResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        return ResponseEntity.ok(authService.forgotPassword(request));
    }

    // ---------- Reset Password (public) ----------
    @PostMapping("/reset-password")
    public ResponseEntity<ApiMessageResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(authService.resetPassword(request));
    }
}