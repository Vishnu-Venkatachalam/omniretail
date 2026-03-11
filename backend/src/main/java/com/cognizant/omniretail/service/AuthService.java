package com.cognizant.omniretail.service;

import com.cognizant.omniretail.dto.request.ForgotPasswordRequest;
import com.cognizant.omniretail.dto.request.LoginRequest;
import com.cognizant.omniretail.dto.request.ResetPasswordRequest;
import com.cognizant.omniretail.dto.response.ApiMessageResponse;
import com.cognizant.omniretail.dto.response.AuthResponse;
import com.cognizant.omniretail.model.User;
import com.cognizant.omniretail.repository.UserRepository;
import com.cognizant.omniretail.security.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuditService auditService;

    // ----------------------------
    // In-memory OTP store (Option 1)
    // ----------------------------
    private final ConcurrentMap<String, PasswordResetToken> resetTokens = new ConcurrentHashMap<>();

    @Data
    @AllArgsConstructor
    private static class PasswordResetToken {
        private String otp;
        private LocalDateTime expiresAt;
        private LocalDateTime issuedAt;
    }

    // ----------------------------
    // Login
    // ----------------------------
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail().trim().toLowerCase())
                .orElseThrow(() -> {
                    auditService.log(null, "LOGIN_FAILED", "/api/auth/login", "email_not_found");
                    return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
                });

        if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
            auditService.log(user.getUserId(), "LOGIN_FAILED", "/api/auth/login", "user_inactive");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is inactive");
        }

        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPasswordHash());

        if (!passwordMatches) {
            auditService.log(user.getUserId(), "LOGIN_FAILED", "/api/auth/login", "wrong_password");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        String accessToken = jwtUtil.generateAccessToken(user);

        auditService.log(user.getUserId(), "LOGIN_SUCCESS", "/api/auth/login", "login_success");

        return AuthResponse.builder()
                .accessToken(accessToken)
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole().name())
                .build();
    }

    // ----------------------------
    // Logout (stateless)
    // ----------------------------
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null || "anonymousUser".equals(authentication.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElse(null);
        Long userId = user != null ? user.getUserId() : null;

        auditService.log(userId, "LOGOUT_SUCCESS", "/api/auth/logout", "email=" + email);
    }

    // ----------------------------
    // Forgot Password (generate OTP and print to console)
    // ----------------------------
    public ApiMessageResponse forgotPassword(ForgotPasswordRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        // Always return generic response to avoid user enumeration.
        // Only generate OTP if user exists.
        userRepository.findByEmail(email).ifPresent(user -> {
            // Basic cooldown: allow new OTP but always overwrite previous (simplest approach)
            String otp = String.valueOf(new Random().nextInt(900000) + 100000);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expiry = now.plusMinutes(5);

            resetTokens.put(email, new PasswordResetToken(otp, expiry, now));

            // Simulate sending OTP via email/SMS by printing to console
            System.out.println("🔔 OTP for password reset (" + email + ") = " + otp + " (expires in 5 minutes)");

            // Audit (userId known)
            auditService.log(user.getUserId(), "FORGOT_PASSWORD", "/api/auth/forgot-password", "otp_sent");
        });

        return new ApiMessageResponse("If the email is registered, an OTP has been sent.");
    }

    // ----------------------------
    // Reset Password (verify OTP)
    // ----------------------------
    public ApiMessageResponse resetPassword(ResetPasswordRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        PasswordResetToken token = resetTokens.get(email);
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid or expired OTP");
        }

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            resetTokens.remove(email);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid or expired OTP");
        }

        if (!token.getOtp().equals(request.getOtp().trim())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid or expired OTP");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid or expired OTP"));

        // Update password
        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        // Invalidate OTP after use
        resetTokens.remove(email);

        // Audit
        auditService.log(user.getUserId(), "PASSWORD_RESET", "/api/auth/reset-password", "success");

        return new ApiMessageResponse("Password reset successful.");
    }
}