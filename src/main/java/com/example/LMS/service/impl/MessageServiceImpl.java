package com.example.LMS.service.impl;

import com.example.LMS.service.MessageService;
import com.twilio.Twilio;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final String accountSid;
    private final String authToken;
    private final String twilioNumber;
    private final JavaMailSender mailSender;

    public MessageServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        this.accountSid = dotenv.get("TWILIO_ACCOUNT_SID");
        this.authToken = dotenv.get("TWILIO_AUTH_TOKEN");
        this.twilioNumber = dotenv.get("TWILIO_PHONE_NUMBER");
    }

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
            Message.creator(
                    new com.twilio.type.PhoneNumber(toWhatsapp),
                    new com.twilio.type.PhoneNumber(twilioNumber),
                    message
            ).create();

            log.info("WhatsApp message sent successfully to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send WhatsApp message to {}: {}", to, e.getMessage(), e);
            throw new RuntimeException("Unable to send WhatsApp message", e);
        }
    }
}
