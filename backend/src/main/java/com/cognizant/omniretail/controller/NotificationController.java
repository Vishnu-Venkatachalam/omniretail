package com.cognizant.omniretail.controller;

import com.cognizant.omniretail.model.Notification;
import com.cognizant.omniretail.model.enums.NotificationStatus;
import com.cognizant.omniretail.service.NotificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/omniretail/notifications")
@SecurityRequirement(name = "bearerAuth")
public class NotificationController {

    @Autowired
    NotificationService notif_service;

    //1. sending notifications (no userId)
    @PostMapping("/send")
    public Notification sendNotification(Notification notification){
        return notif_service.sendNotification(notification);
    }

    //2. get all notifications of a user
    @GetMapping("/user/{userId}/viewAll")
    public List<Notification> getNotificationByUser(@PathVariable Long userId){
        return notif_service.getAllNotifications(userId);
    }


    //3. marking notification as read
    @PatchMapping("/{notificationId}/read")
    public Notification markNotificationAsRead(@PathVariable Long notificationId) throws Exception {
        return notif_service.markNotificationAsRead(notificationId);
    }

    //4. get all notifications of a user by status (useful in filtering)
    @GetMapping("/user/{userId}/status/{status}")
    public List<Notification> getNotificationsByUserAndStatus(@PathVariable Long userId, @PathVariable NotificationStatus status){
        return notif_service.getNotificationsByUserAndStatus(userId, status);
    }
}
