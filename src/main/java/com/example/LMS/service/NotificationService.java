package com.example.LMS.service;

import com.example.LMS.domain.response.NotificationDto;


import java.util.List;

public interface NotificationService {

    List<NotificationDto> checkOverdueLoans();

    List<NotificationDto> getNotificationsByStudent(Long id);
}
