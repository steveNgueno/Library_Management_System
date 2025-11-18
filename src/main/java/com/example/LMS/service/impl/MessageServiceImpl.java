package com.example.LMS.service.impl;

import com.example.LMS.service.MessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    @Override
    public void sendEmail(String to, String subject, String text) {

    }

    @Override
    public void sendWhatsappMessage(String to, String message) {

    }
}
