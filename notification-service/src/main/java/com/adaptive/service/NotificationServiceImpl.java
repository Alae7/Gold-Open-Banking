package com.adaptive.service;


import com.adaptive.dto.NotificationRequestDto;
import com.adaptive.entity.Notification;
import com.adaptive.model.CustomerResponseDto;
import com.adaptive.model.TransactionResponseDto;
import com.adaptive.repository.NotificationRepository;
import com.adaptive.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class NotificationServiceImpl{

    @Autowired
    private NotificationRepository notificationRepository;


    @KafkaListener(topics = "notification_topic", groupId = "my-gold")
    public void processNotificationEvent(@Header(KafkaHeaders.RECEIVED_KEY) String key, @Payload NotificationRequestDto notificationRequestDto) {
        System.out.println("Received Key: " + key + ", notification: " + notificationRequestDto);

        Notification notification = Utils.createNotification(notificationRequestDto);

        TransactionResponseDto transactionResponseDto = Utils.getTransaction(notification.getTransactionUuid());

        CustomerResponseDto targetClient = Utils.getClient(transactionResponseDto.getTargetRib());
        CustomerResponseDto sourceClient = Utils.getClient(transactionResponseDto.getSourceRib());

        notification.setTargetRib(transactionResponseDto.getTargetRib());
        notification.setSourceRib(transactionResponseDto.getSourceRib());
        notification.setStatus(Utils.sendMessage(targetClient,sourceClient,transactionResponseDto));

        notificationRepository.save(notification);
        System.out.println(notification.getStatus());


    }

}
