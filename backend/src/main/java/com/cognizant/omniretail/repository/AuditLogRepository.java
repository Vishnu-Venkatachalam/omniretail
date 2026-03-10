package com.cognizant.omniretail.repository;

import com.cognizant.omniretail.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}