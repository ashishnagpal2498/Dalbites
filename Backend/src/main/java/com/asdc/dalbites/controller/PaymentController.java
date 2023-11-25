package com.asdc.dalbites.controller;

import com.asdc.dalbites.model.DTO.PaymentIntentRequestDTO;
import com.asdc.dalbites.service.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/create-payment-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestBody PaymentIntentRequestDTO paymentRequestDTO) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(paymentService.createPaymentIntent(paymentRequestDTO));
		} catch(StripeException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(e);
		}
    }
}
