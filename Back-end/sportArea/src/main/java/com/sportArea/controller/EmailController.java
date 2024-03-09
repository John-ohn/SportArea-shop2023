package com.sportArea.controller;

import com.sportArea.entity.MailSubscription;
import com.sportArea.entity.dto.GeneralResponse;
import com.sportArea.entity.dto.logger.GeneralLogg;
import com.sportArea.service.MailSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {

    private final GeneralLogg generalLogg;


    private final MailSubscriptionService mailSubscriptionService;

    @PostMapping("/subscription")
    public ResponseEntity<GeneralResponse> sendAfterSubscription(@RequestBody MailSubscription mailRequest) throws MessagingException, IOException {

        mailSubscriptionService.addNewSubscription(mailRequest);

        generalLogg.getLoggerControllerInfo("EmailController",
                "sendAfterSubscription",
                "/send/subscription",
                "message (The subscription was successful.) and Save new subscription with user email: " + mailRequest.getEmail());

        GeneralResponse generalResponse = new GeneralResponse(
                HttpStatus.OK.value(),
                "The subscription was successful.");

        return ResponseEntity.ok(generalResponse);
    }



}
