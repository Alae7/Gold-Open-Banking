package com.adaptive.mapper;

import com.adaptive.dto.NotificationRequestDto;
import com.adaptive.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper( componentModel = "spring")
public interface NotificationMapper {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    Notification toEntity(NotificationRequestDto notificationRequestDto);

}
