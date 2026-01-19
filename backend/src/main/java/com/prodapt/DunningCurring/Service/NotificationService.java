package com.prodapt.DunningCurring.Service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.DunningCurring.DAO.NotificationRepository;
import com.prodapt.DunningCurring.Entity.Notification;
import com.prodapt.DunningCurring.Entity.TelecomService;

@Service
public class NotificationService {

	 @Autowired
    private final NotificationRepository notificationRepository;

   
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    
    public List<Notification> getByService(Long serviceId) {
        return notificationRepository.findByServiceId(serviceId);
    }


    public void send(TelecomService service, String channel, String message) {

        Notification notification = new Notification();
        notification.setService(service);
        notification.setChannel(channel);
        notification.setMessage(message);
        notification.setDeliveryStatus("SENT");

        notificationRepository.save(notification);
    }
}
