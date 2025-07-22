package com.adaptive.repository;


import com.adaptive.entity.NotificationCompte;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface NotificationCompteRepository extends MongoRepository<NotificationCompte, String> {
}
