package com.asdc.dalbites.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.asdc.dalbites.model.DTO.PaymentIntentRequestDTO;
import com.stripe.exception.StripeException;

@Service
public interface PaymentService {
	public Map<String, String> createPaymentIntent(PaymentIntentRequestDTO paymentIntentRequestDTO) throws StripeException;
}
