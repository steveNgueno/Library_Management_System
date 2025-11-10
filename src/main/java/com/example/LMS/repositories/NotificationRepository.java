package com.example.LMS.repositories;

import com.example.LMS.models.Notification;
import com.example.LMS.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByStudent(Student student);
}
