package com.adaptive.service;


import com.adaptive.dto.CustomerResponseDto;
import com.adaptive.dto.NotificationRequestDto;
import com.adaptive.dto.Notification_CompteRequestDto;
import com.adaptive.entity.Notification;
import com.adaptive.entity.NotificationCompte;
import com.adaptive.model.CompteResponseDto;
import com.adaptive.model.TransactionResponseDto;
import com.adaptive.repository.NotificationCompteRepository;
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

    @Autowired
    private NotificationCompteRepository notificationCompteRepository;

    @Autowired
    private Utils utils;


    @KafkaListener(topics = "transaction_topic", groupId = "my-gold")
    public void processTransactionEvent(@Header(KafkaHeaders.RECEIVED_KEY) String key, @Payload NotificationRequestDto notificationRequestDto) {
        System.out.println("Received Key: " + key + ", notification: " + notificationRequestDto);

        Notification notification = utils.createNotification(notificationRequestDto);

        TransactionResponseDto transactionResponseDto = utils.getTransaction(notification.getTransactionUuid());

        CustomerResponseDto targetClient = utils.getClient(transactionResponseDto.getTargetRib());
        CustomerResponseDto sourceClient = utils.getClient(transactionResponseDto.getSourceRib());

        notification.setTargetRib(transactionResponseDto.getTargetRib());
        notification.setSourceRib(transactionResponseDto.getSourceRib());
        notification.setStatus(utils.sendMessage(targetClient,sourceClient,transactionResponseDto));

        notificationRepository.save(notification);
        System.out.println(notification.getStatus());


    }


    @KafkaListener(topics = "compte_topic", groupId = "my-gold")
    public void processCompteEvent(@Header(KafkaHeaders.RECEIVED_KEY) String key, @Payload Notification_CompteRequestDto notificationCompteRequestDto) {

        CompteResponseDto compteResponseDto = utils.getCompte(notificationCompteRequestDto.getCompteRib());
        CustomerResponseDto customerResponseDto = utils.getClient(notificationCompteRequestDto.getCompteRib());
        NotificationCompte notificationCompte = utils.createNotificationCompte(notificationCompteRequestDto);

        switch (key){

            case "CREATION" -> notificationCompte.setStatus(utils.sendAccountCreationMessage(customerResponseDto,compteResponseDto));

            case "ACTIVATION", "DEACTIVATION" ->  notificationCompte.setStatus(utils.sendAccountStatusMessage(compteResponseDto,key,customerResponseDto));

            default -> System.out.println("Unknown event key: " + key);

        }

        notificationCompteRepository.save(notificationCompte);

    }

    @KafkaListener(topics = "anomaly_topic", groupId = "my-gold")
    public void processAnomalyEvent(@Header(KafkaHeaders.RECEIVED_KEY) String key, @Payload Notification_CompteRequestDto notificationCompteRequestDto) {

        CompteResponseDto compteResponseDto = utils.getCompte(notificationCompteRequestDto.getCompteRib());
        CustomerResponseDto customerResponseDto = utils.getClient(notificationCompteRequestDto.getCompteRib());
        NotificationCompte notificationCompte = utils.createNotificationCompte(notificationCompteRequestDto);

        notificationCompte.setStatus(utils.sendAnomalyMessage(customerResponseDto,compteResponseDto));

        notificationCompteRepository.save(notificationCompte);
        System.out.println(notificationCompte.getStatus());


    }


}
