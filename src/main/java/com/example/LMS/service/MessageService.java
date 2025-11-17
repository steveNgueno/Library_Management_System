package com.example.LMS.service;

public interface MessageService {

    void sendEmail(String to, String subject, String text);

    void sendWhatsappMessage(String to, String message);

}
