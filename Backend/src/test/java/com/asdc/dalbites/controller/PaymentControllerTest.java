package com.asdc.dalbites.controller;
import com.asdc.dalbites.controller.PaymentController;
import com.asdc.dalbites.model.DTO.PaymentIntentRequestDTO;
import com.asdc.dalbites.service.PaymentService;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.StripeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @Test
    void createPaymentIntent_Success() throws StripeException {
        PaymentIntentRequestDTO paymentRequestDTO = createPaymentIntentRequestDTO();
        Map<String, String> sampleResponse = createSamplePaymentIntentResponse();
        when(paymentService.createPaymentIntent(any())).thenReturn(sampleResponse);
        ResponseEntity<?> response = paymentController.createPaymentIntent(paymentRequestDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleResponse, response.getBody());
        verify(paymentService, times(1)).createPaymentIntent(paymentRequestDTO);
    }

    @Test
    void createPaymentIntent_InternalServerError() throws StripeException {
        PaymentIntentRequestDTO paymentRequestDTO = createPaymentIntentRequestDTO();
        when(paymentService.createPaymentIntent(any())).thenThrow(AuthenticationException.class);
        ResponseEntity<?> response = paymentController.createPaymentIntent(paymentRequestDTO);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof StripeException);
        verify(paymentService, times(1)).createPaymentIntent(paymentRequestDTO);
    }



    private PaymentIntentRequestDTO createPaymentIntentRequestDTO() {
        PaymentIntentRequestDTO requestDTO = new PaymentIntentRequestDTO();
        requestDTO.setPaymentMethodType("card");
        requestDTO.setCurrency("usd");
        requestDTO.setAmount("1000");
        return requestDTO;
    }

    private Map<String, String> createSamplePaymentIntentResponse() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("paymentId", "12345");
        return response;
    }
}
