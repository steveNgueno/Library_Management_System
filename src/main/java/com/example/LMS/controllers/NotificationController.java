package com.example.LMS.controllers;

import com.example.LMS.dtos.NotificationDto;
import com.example.LMS.services.impl.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationServiceImpl notificationServiceImpl;

    @PostMapping("/check")
    public ResponseEntity<?> checkOverdueManually(){
        notificationServiceImpl.checkOverdueLoans();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/all/{id}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByStudent(@PathVariable Long id){
        return ResponseEntity.ok(notificationServiceImpl.getNotificationsByStudent(id));
    }
}
