package com.sportArea.controller;

import com.sportArea.model.RequestDTO;
import com.sportArea.model.ResponseDTO;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentIntentController {

    @PostMapping("/create-payment-intent")
    public ResponseDTO createPaymentIntent(@RequestBody RequestDTO request) throws StripeException {

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(request.getAmount())
                .putMetadata("productName", request.getProductName())
                .setCurrency("usd")
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams
                                .AutomaticPaymentMethods
                                .builder()
                                .setEnabled(true)
                                .build()
                ).build();
        PaymentIntent intent = PaymentIntent.create(params);

        return new ResponseDTO(intent.getId(), intent.getClientSecret());

    }
}
