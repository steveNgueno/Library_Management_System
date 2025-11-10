package com.example.LMS.services.impl;

import com.example.LMS.dtos.NotificationDto;
import com.example.LMS.exceptions.StudentNotFoundException;
import com.example.LMS.mappers.NotificationMapper;
import com.example.LMS.models.Book;
import com.example.LMS.models.Loan;
import com.example.LMS.models.Notification;
import com.example.LMS.models.Student;
import com.example.LMS.repositories.LoanRepository;
import com.example.LMS.repositories.NotificationRepository;
import com.example.LMS.repositories.StudentRepository;
import com.example.LMS.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final EmailServiceImpl emailService;
    private final NotificationRepository notificationRepository;
    private final LoanRepository loanRepository;
    private final StudentRepository studentRepository;
    private final NotificationMapper mapper;

    @Scheduled(cron= "0 0 8 * * ?")
    @Override
    public void checkOverdueLoans() {

        List<Loan> overdueLoans = loanRepository.findByExpectedReturnDateBeforeAndActive(LocalDate.now(), true);

        for(Loan loan : overdueLoans ){

            Student student = loan.getStudent();
            Book book = loan.getBook();
            String subject = "Overdue return of book";
            String message = "Good morning "+student.getFirstname()+ ", \n\n" + "The expected return date for the book '" + book.getTitle() + "' has passed since "+loan.getExpectedReturnDate() + ".\n" + "Please return it as soon as possible";

            emailService.sendEmail(
                    student.getEmail(),
                    subject,
                    message
            );

            emailService.sendEmail(
                    "",
                    "Book not return : "+book.getTitle(),
                    "Student "+student.getFirstname()+" "+student.getLastname()+ "don't return the book '"+book.getTitle() + "' in the expected return date (" +loan.getExpectedReturnDate() +")."
            );


            Notification notification = Notification.builder()
                    .title(subject)
                    .message(message)
                    .student(student)
                    .build();

            notificationRepository.save(notification);
        }

    }

    @Override
    public List<NotificationDto> getNotificationsByStudent(Long id) {

        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException("Student not found with id: "+id));

        List<Notification> notifications = student.getNotifications();

        return mapper.toDtoList(notifications);
    }
}
