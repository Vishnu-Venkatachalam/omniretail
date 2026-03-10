package com.cognizant.omniretail.service;

import com.cognizant.omniretail.model.Notification;
import com.cognizant.omniretail.model.enums.NotificationStatus;
import com.cognizant.omniretail.repository.NotificationRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notif_repo;

    //1. send a notification
    public Notification sendNotification(Notification notification){
        return notif_repo.save(notification);
    }

    //2. find all notifications of a user regardless of status
    public List<Notification> getAllNotifications(Long userId){
        return notif_repo.findNotificationsByUserId(userId);
    }

    //3. mark a notification as read
    public Notification markNotificationAsRead(Long notificationId) throws Exception {
        Notification notification = notif_repo.findById(notificationId)
                .orElseThrow(()->new Exception("Cannot find the notification"));

        notification.setStatus(NotificationStatus.READ);
        return notif_repo.save(notification);
    }

    //4. find all notifications of a user and notification status
    public List<Notification> getNotificationsByUserAndStatus(Long userId, NotificationStatus status){
        return notif_repo.findNotifByUserAndStatus(userId, status);
    }
}
