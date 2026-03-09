package com.cognizant.omniretail.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;

    // who performed the action (nullable for unknown user, like failed login)
    private Long userId;

    @Column(nullable = false, length = 100)
    private String action;      // e.g., LOGIN_SUCCESS, USER_CREATED

    @Column(nullable = false, length = 150)
    private String resource;    // e.g., /api/auth/login or USER:12

    @Column(nullable = false)
    private LocalDateTime timestamp;

    // Keep it simple for mini project: store metadata as text (JSON-like string)
    @Column(columnDefinition = "TEXT")
    private String metadata;

    @PrePersist
    public void prePersist() {
        timestamp = LocalDateTime.now();
    }
}