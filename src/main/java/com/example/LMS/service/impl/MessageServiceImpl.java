package com.example.LMS.service.impl;

import com.example.LMS.service.MessageService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService
{

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.whatsapp-from}")
    private String whatsappFrom;

    private final JavaMailSender mailSender;


    @PostConstruct
    public void initTwilio() {
        if (accountSid != null && authToken != null) {
            Twilio.init(accountSid, authToken);
            log.info("Twilio initialized with SID: {}", accountSid.substring(0, 8) + "...");
        } else {
            log.warn("Twilio credentials missing — WhatsApp disabled!");
        }
    }

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

    @Override
    public void sendWhatsappMessage(String to, String message) {
        Message.creator(
                new PhoneNumber("whatsapp:" + to),
                new PhoneNumber(whatsappFrom),
                message
        ).create();

        log.info("whatsapp message sent to : {}", to);
    }
}
