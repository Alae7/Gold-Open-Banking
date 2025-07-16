package com.adaptive.repository;


import com.adaptive.entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {

    List<Notification> findByTargetRibAndNotificationType(Long rib, String notificationType);
    List<Notification> findByTargetRib(Long rib);
    List<Notification> findBySourceRibAndNotificationType(Long rib, String notificationType);
    List<Notification> findBySourceRib(Long rib);
    Notification findByUuid(String uuid);
}
