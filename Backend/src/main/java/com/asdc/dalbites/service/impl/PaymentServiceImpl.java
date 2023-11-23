package com.asdc.dalbites.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.asdc.dalbites.model.DTO.PaymentIntentRequestDTO;
import com.asdc.dalbites.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Override
	public Map<String, String> createPaymentIntent(PaymentIntentRequestDTO paymentIntentRequestDTO) throws StripeException {
		try {
			Stripe.apiKey = "sk_test_51OE1QOAxY201a6THmRb6QUUvbQGe7Ec36W0jK6wrHlis3nUHLgquwHGslPI3aDrwpblmugDy49EpukU5dmyHzG66002ribyeu9";
			PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
        		    .setAmount(1099L)
        		    .setCurrency("cad")
        		    .addPaymentMethodType("card")
        		    .build();

			PaymentIntent intent = PaymentIntent.create(params);
			String clientSecret = intent.getClientSecret();
			Map<String, String> responseParams = new HashMap<>();
			responseParams.put("clientSecret", clientSecret);
			return responseParams;
		} catch (StripeException e) {
            e.printStackTrace();
            throw e;
        }
	}
}
