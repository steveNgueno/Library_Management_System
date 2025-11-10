package com.example.LMS.mappers;

import com.example.LMS.dtos.NotificationDto;
import com.example.LMS.models.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel ="spring")
public interface NotificationMapper {

    @Mapping(source="createAt", target="createAt")
    List<NotificationDto> toDtoList(List<Notification> notification);
}
