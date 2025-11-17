package com.example.LMS.service;

import com.example.LMS.domain.request.NotificationDto;


import java.util.List;

public interface NotificationService {

    void checkOverdueLoans();

    List<NotificationDto> getNotificationsByStudent(Long id);
}
