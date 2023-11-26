package com.asdc.dalbites.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Map;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import com.asdc.dalbites.model.DTO.PaymentIntentRequestDTO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@SpringBootTest
public class PaymentServiceImplTest {

    @Mock
    private PaymentIntent paymentIntentMock;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Disabled("Makes external Stripe API call")
    @Test
    public void testCreatePaymentIntent() throws StripeException {
    	Stripe.apiKey = "sk_test_dummykey";
    	PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
    		    .setAmount(1099L)
    		    .setCurrency("cad")
    		    .addPaymentMethodType("card")
    		    .build();
    	when(PaymentIntent.create(Mockito.any(PaymentIntentCreateParams.class))).thenReturn(paymentIntentMock);
        when(paymentIntentMock.getClientSecret()).thenReturn("testClientSecret");

        PaymentIntentRequestDTO paymentIntentRequestDTO = new PaymentIntentRequestDTO();

        Map<String, String> result = paymentService.createPaymentIntent(paymentIntentRequestDTO);

        assertEquals("testClientSecret", result.get("clientSecret"));
    }
}