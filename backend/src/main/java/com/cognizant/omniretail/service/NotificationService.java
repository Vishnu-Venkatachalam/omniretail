package com.cognizant.omniretail.service;

import com.cognizant.omniretail.model.Notification;
import com.cognizant.omniretail.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notif_repo;


    public Notification sendNotification(Notification notification){
        return notif_repo.save(notification);
    }

    public void readNotification(Notification notification, Long notificationId){
        Optional<Notification> optionalNotif = notif_repo.findById(notificationId);
        if(optionalNotif.isPresent()) {
            Notification readNotification = optionalNotif.get();
            notif_repo.save(readNotification);
        }
        else{
            System.out.println("Oops! Can't find the notification");
        }

    }
}
