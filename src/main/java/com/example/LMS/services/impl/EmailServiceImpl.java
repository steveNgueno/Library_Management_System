package com.example.LMS.services.impl;

import com.example.LMS.services.EmailService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@AllArgsConstructor
public class EmailServiceImpl implements EmailService
{
    private JavaMailSender mailSender;
    @Override
    public void sendEmail(String to, String subject, String text) {

      try{
          SimpleMailMessage message = new SimpleMailMessage();

          message.setTo(to);
          message.setSubject(subject);
          message.setText(text);
          message.setFrom("nguenosteve3@gmail.com");

          mailSender.send(message);
          log.info("Email sent to : {}", to);

      }catch(MailException e){
          log.error("An error occurred while sending of email : {}", String.valueOf(e.getCause()));
      }
    }
}
