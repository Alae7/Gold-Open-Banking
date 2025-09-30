package com.adaptive.repository;


import com.adaptive.entity.Notification;
import com.adaptive.entity.NotificationCredit;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface NotificationCreditRepository extends MongoRepository<NotificationCredit, String> {

}
