package com.adaptive.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Document(collection = "notification_Compte")
public class NotificationCompte extends BaseModel{

    private Long compteRib;
    private String notificationType;

}
