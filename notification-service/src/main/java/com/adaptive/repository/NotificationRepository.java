package com.adaptive.repository;


import com.adaptive.entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface NotificationRepository extends MongoRepository<Notification, String> { }
