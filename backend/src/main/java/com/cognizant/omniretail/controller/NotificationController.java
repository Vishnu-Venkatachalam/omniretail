package com.cognizant.omniretail.controller;

import com.cognizant.omniretail.model.Notification;
import com.cognizant.omniretail.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/omniretail/notification")
public class NotificationController {

    @Autowired
    NotificationService notif_service;

    @PostMapping("/send")
    public Notification sendNotification(Notification notification){
        return notif_service.sendNotification(notification);
    }

}
