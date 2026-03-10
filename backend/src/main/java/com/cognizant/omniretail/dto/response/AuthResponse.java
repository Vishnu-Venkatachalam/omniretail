package com.cognizant.omniretail.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String accessToken;
    private Long userId;
    private String name;
    private String email;
    private String phone;
    private String role;
}