package com.example.LMS.config;

import com.sendgrid.SendGrid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class SendGridConfig {
    private final String apiKey;
    private final String fromEmail;

    public SendGridConfig(
            @Value("${sendgrid.api-key}") String apiKey,
            @Value("${sendgrid.from-email}") String fromEmail) {
        this.apiKey = apiKey;
        this.fromEmail = fromEmail;
    }

    @Bean
    public SendGrid sendGridClient() {
        return new SendGrid(apiKey);
    }
}
