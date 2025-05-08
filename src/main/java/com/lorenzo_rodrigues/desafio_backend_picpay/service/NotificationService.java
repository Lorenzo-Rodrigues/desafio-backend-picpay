package com.lorenzo_rodrigues.desafio_backend_picpay.service;

import com.lorenzo_rodrigues.desafio_backend_picpay.client.NotificationClient;
import com.lorenzo_rodrigues.desafio_backend_picpay.entity.Transfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {
    private final NotificationClient notificationClient;

    public NotificationService(NotificationClient notificationClient) {
        this.notificationClient = notificationClient;
    }

    public void sendNotification(Transfer transfer) {

        try {
            log.info("sending notification...");
            notificationClient.sendNotification(transfer);
        }catch (Exception e){
            log.error("error while sending notification", e);
        }
    }
}
