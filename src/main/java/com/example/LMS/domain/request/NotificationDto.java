package com.example.LMS.domain.request;

import java.time.LocalDateTime;

public record NotificationDto(String title, String message, LocalDateTime createAt) {
}
