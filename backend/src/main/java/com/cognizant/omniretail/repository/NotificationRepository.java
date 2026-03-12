package com.cognizant.omniretail.repository;

import com.cognizant.omniretail.model.Notification;
import com.cognizant.omniretail.model.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    @Query("select n from Notification n WHERE n.user.userId =:userId order by n.createdAt DESC")
    List<Notification> findNotificationsByUserId(Long userId);

    @Query("select n from Notification n WHERE n.user.userId =:userId AND n.status=:status")
    List<Notification> findNotifByUserAndStatus(Long userId, NotificationStatus status);
}
