package com.example.LMS.service.impl;

import com.example.LMS.config.SendGridConfig;
import com.example.LMS.config.TwilioConfig;
import com.example.LMS.domain.Enum.Action;
import com.example.LMS.domain.Enum.Status;
import com.example.LMS.service.HistoryService;
import com.example.LMS.service.MessageService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;

import java.io.IOException;

@Service
@Slf4j
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final SendGrid sendgrid;
    private final SendGridConfig sendGrid;
    private final TwilioConfig twilio;
    private final HistoryService historyService;


    @Override
    public void sendEmail(String recipient, String subject, String body) {
        try{
            Email from = new Email(sendGrid.getFromEmail());
            Email to = new Email(recipient);

            Content content = new Content("text/plain", body);

            Mail mail = new Mail(from, subject, to, content);

            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendgrid.api(request);

            String messageId = response.getHeaders().getOrDefault("X-Message-Id", "unknown");
            int statusCode = response.getStatusCode();

            log.info("Email sent from: {} → status: {}, messageId: {}, to: {}",
                    sendGrid.getFromEmail(), statusCode, messageId, recipient);

            historyService.create(Status.SUCCESS, Action.SEND_NOTIFICATION,String.format("Notification email sent to %s", recipient));

        } catch (IOException e) {

            log.error("Error while sending email to {}", recipient, e);
            historyService.create(Status.FAILED, Action.SEND_NOTIFICATION,String.format("Error while sending email %s", recipient));

        } catch(Exception unexpected ){
            log.error("Unexpected error while sending emailMS", unexpected);
        }
    }

  @Override
    public void sendWhatsappMessage(String to, String message) {

        String toWhatsapp = "WhatsApp:" + to.replaceAll("[^0-9]", "");

        try {
            Message.creator(
                    new com.twilio.type.PhoneNumber(toWhatsapp),
                    new com.twilio.type.PhoneNumber(twilio.getTwilioNumber()),
                    message
            ).create();

            log.info("WhatsApp message sent successfully to: {}", to);
            historyService.create(Status.SUCCESS, Action.SEND_NOTIFICATION,String.format("WhatsApp message sent successfully to: %s ", to));

        } catch (Exception e) {

            log.error("Failed to send WhatsApp message to {}: {}", to, e.getMessage(), e);
            historyService.create(Status.FAILED, Action.SEND_NOTIFICATION,String.format("Failed to send WhatsApp message to: %s ", to));

            throw new RuntimeException("Unable to send WhatsApp message", e);
        }
    }
}
