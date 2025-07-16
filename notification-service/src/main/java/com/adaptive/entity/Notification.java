package com.adaptive.entity;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Document(collection = "notification")
public class Notification extends BaseModel {

    private String transactionUuid;
    private Long targetRib;
    private Long sourceRib;
    private String notificationType;

}
