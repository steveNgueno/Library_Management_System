package com.example.LMS.dtos;

import java.time.LocalDateTime;

public record NotificationDto(String title, String message, LocalDateTime createAt) {
}
