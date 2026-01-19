package com.prodapt.DunningCurring.Controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prodapt.DunningCurring.Entity.Notification;
import com.prodapt.DunningCurring.Service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/service/{serviceId}")
    public List<Notification> getNotifications(@PathVariable Long serviceId) {
        return notificationService.getByService(serviceId);
    }
}
