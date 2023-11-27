package com.asdc.dalbites.service.impl;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import com.asdc.dalbites.model.DTO.PaymentIntentRequestDTO;
import com.stripe.exception.StripeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class PaymentServiceImplTest {
    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePaymentIntent() throws StripeException {
        PaymentIntentRequestDTO paymentIntentRequestDTO = new PaymentIntentRequestDTO();
        paymentIntentRequestDTO.setAmount("10");
        paymentIntentRequestDTO.setCurrency("GBP");
        paymentIntentRequestDTO.setPaymentMethodType("Payment Method Type");
        paymentService.createPaymentIntent(paymentIntentRequestDTO);
    }

    @Test
    void testCreatePaymentIntent2() throws StripeException {
        PaymentIntentRequestDTO paymentIntentRequestDTO = mock(PaymentIntentRequestDTO.class);
        doNothing().when(paymentIntentRequestDTO).setAmount((String) any());
        doNothing().when(paymentIntentRequestDTO).setCurrency((String) any());
        doNothing().when(paymentIntentRequestDTO).setPaymentMethodType((String) any());
        paymentIntentRequestDTO.setAmount("10");
        paymentIntentRequestDTO.setCurrency("GBP");
        paymentIntentRequestDTO.setPaymentMethodType("Payment Method Type");
        paymentService.createPaymentIntent(paymentIntentRequestDTO);
    }
}

