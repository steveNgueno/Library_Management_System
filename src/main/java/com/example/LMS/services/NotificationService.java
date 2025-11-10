package com.example.LMS.services;

import com.example.LMS.dtos.NotificationDto;


import java.util.List;

public interface NotificationService {

    void checkOverdueLoans();

    List<NotificationDto> getNotificationsByStudent(Long id);
}
