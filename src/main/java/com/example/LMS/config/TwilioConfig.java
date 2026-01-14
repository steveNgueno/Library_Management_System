package com.example.LMS.config;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Getter
public class TwilioConfig {
    private final String accountSid;
    private final String authToken;
    private final String twilioNumber;

    public TwilioConfig(
            @Value("${twilio.account-sid}")String accountSid,
            @Value("${twilio.auth-token}")String authToken,
            @Value("${twilio.from-whatsapp}")String twilioNumber) {
        this.accountSid = accountSid;
        this.authToken = authToken;
        this.twilioNumber = twilioNumber;
    }

    @PostConstruct
    public void initTwilio(){
        Twilio.init(accountSid, authToken);
        log.info("Twilio initialized with Account SID: {}", accountSid);
    }
}
