package com.cognizant.omniretail.model;

import com.cognizant.omniretail.model.enums.NotificationCategory;
import com.cognizant.omniretail.model.enums.NotificationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "Notification"
)
@Builder
public class Notification {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "notification_id", unique = true)
    private Long notificationId;

    @Column(name = "message", nullable = false, length = 150)
    private String message;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "category", nullable = false)
    private NotificationCategory category;

    @Column(name = "status", nullable = false)
    private NotificationStatus status;

    @CreationTimestamp //sets current time into createAt
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id"
            //referencedColumnName = "userId"
    )
    private User user;
}
