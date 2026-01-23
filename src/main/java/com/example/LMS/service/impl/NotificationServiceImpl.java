package com.example.LMS.service.impl;

import com.example.LMS.domain.response.NotificationDto;
import com.example.LMS.exception.UserNotFoundException;
import com.example.LMS.mapper.NotificationMapper;
import com.example.LMS.domain.model.Book;
import com.example.LMS.domain.model.Loan;
import com.example.LMS.domain.model.Notification;
import com.example.LMS.domain.model.Student;
import com.example.LMS.repository.LoanRepository;
import com.example.LMS.repository.NotificationRepository;
import com.example.LMS.repository.StudentRepository;
import com.example.LMS.service.MessageService;
import com.example.LMS.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final MessageService messageService;
    private final NotificationRepository notificationRepository;
    private final LoanRepository loanRepository;
    private final StudentRepository studentRepository;
    private final NotificationMapper mapper;

    @Scheduled(cron= "0 0 9 * * ?")
    @Override
    public List<NotificationDto> checkOverdueLoans() {

        List<Loan> overdueLoans = loanRepository.findByExpectedReturnDateBeforeAndActive(LocalDate.now(), true);
        List<Notification> notifications = new ArrayList<>();

        for(Loan loan : overdueLoans ){

            Student student = loan.getStudent();
            Book book = loan.getBook();
            String subject = "Overdue return of book";
            String message = "Good morning "+student.getFirstname()+ ", \n\n" + "The expected return date for the book '" + book.getTitle() + "' has passed since "+loan.getExpectedReturnDate() + ".\n" + "Please return it as soon as possible";

            messageService.sendEmail(
                    student.getEmail(),
                    subject,
                    message
            );

          messageService.sendEmail(
                    "yougangyollande11@gmail.com",
                    "Book not return : "+book.getTitle(),
                    "Student "+student.getFirstname()+" "+student.getLastname()+ "don't return the book '"+book.getTitle() + "' in the expected return date (" +loan.getExpectedReturnDate() +")."
            );

         /* messageServiceImpl.sendWhatsappMessage(
            student.getPhone(),
            message
          );*/


            Notification notification = Notification.builder()
                    .title(subject)
                    .message(message)
                    .student(student)
                    .build();

            notificationRepository.save(notification);
            notifications.add(notification);
        }
        return mapper.toDtoList(notifications);
    }

    @Override
    public List<NotificationDto> getNotificationsByStudent(Long id) {

        Student student = studentRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        List<Notification> notifications = student.getNotifications();

        return mapper.toDtoList(notifications);
    }
}
