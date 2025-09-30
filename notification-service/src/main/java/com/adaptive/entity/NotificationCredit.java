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
public class NotificationCredit extends BaseModel{

    private String creditUuid;
    private String notificationType;

}
