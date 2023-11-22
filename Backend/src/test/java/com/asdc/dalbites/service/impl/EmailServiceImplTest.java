package com.asdc.dalbites.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class EmailServiceImplTest {

    @Test
    void sendOrderConfirmationEmail_ShouldSendEmail() {
        EmailServiceImpl emailService = new EmailServiceImpl();
        emailService.javaMailSender = mock(JavaMailSender.class);

    }
}

