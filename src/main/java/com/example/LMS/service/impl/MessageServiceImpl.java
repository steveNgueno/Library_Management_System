package com.example.LMS.service.impl;

import com.example.LMS.service.MessageService;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.whatsapp.from}")
    private String fromWhatsapp;

    private final JavaMailSender mailSender;

    @PostConstruct
    public void initTwilio(){
        Twilio.init(accountSid,authToken);
        log.info("Twilio initialized with Account SID: {}", accountSid);
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

        String toWhatsapp = "whatsapp:" + to.replaceAll("[^0-9]", "");

        try {
            Message twilioMessage = Message.creator(
                    new com.twilio.type.PhoneNumber(toWhatsapp),
                    new com.twilio.type.PhoneNumber(fromWhatsapp),
                    message
            ).create();

            log.info("WhatsApp message sent successfully to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send WhatsApp message to {}: {}", to, e.getMessage(), e);
            throw new RuntimeException("Unable to send WhatsApp message", e);
        }
    }
}
