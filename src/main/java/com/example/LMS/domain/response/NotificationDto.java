package com.example.LMS.domain.response;

import java.time.LocalDateTime;

public record NotificationDto(String title, String message,String studentEmail,LocalDateTime createAt) {
}
