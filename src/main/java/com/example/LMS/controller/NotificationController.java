package com.example.LMS.controller;

import com.example.LMS.domain.response.NotificationDto;
import com.example.LMS.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/check")
    public ResponseEntity<List<NotificationDto>> checkOverdueManually(){
        return ResponseEntity.ok(notificationService.checkOverdueLoans());
    }

    @GetMapping("/get/all/{id}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByStudent(@PathVariable Long id){
        return ResponseEntity.ok(notificationService.getNotificationsByStudent(id));
    }
}
