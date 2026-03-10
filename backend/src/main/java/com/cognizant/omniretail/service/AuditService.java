package com.cognizant.omniretail.service;

import com.cognizant.omniretail.model.AuditLog;
import com.cognizant.omniretail.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public void log(Long userId, String action, String resource, String metadata) {
        AuditLog auditLog = AuditLog.builder()
                .userId(userId)
                .action(action)
                .resource(resource)
                .metadata(metadata)
                .build();

        auditLogRepository.save(auditLog);
    }
}