package com.example.LMS.mapper;

import com.example.LMS.domain.request.NotificationDto;
import com.example.LMS.domain.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel ="spring")
public interface NotificationMapper {

    @Mapping(source="createAt", target="createAt")
    List<NotificationDto> toDtoList(List<Notification> notification);
}
