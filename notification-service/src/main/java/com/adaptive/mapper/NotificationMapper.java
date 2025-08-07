package com.adaptive.mapper;

import com.adaptive.dto.NotificationRequestDto;
import com.adaptive.dto.Notification_CompteRequestDto;
import com.adaptive.entity.Notification;
import com.adaptive.entity.NotificationCompte;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper( componentModel = "spring",uses = {NotificationMapper.class})
public interface NotificationMapper {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    Notification toEntity(NotificationRequestDto notificationRequestDto);

    NotificationCompte toNotificationCompte(Notification_CompteRequestDto notificationCompteRequestDto);

}
