package com.example.LMS.repository;

import com.example.LMS.domain.model.Notification;
import com.example.LMS.domain.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByStudent(Student student);
}
