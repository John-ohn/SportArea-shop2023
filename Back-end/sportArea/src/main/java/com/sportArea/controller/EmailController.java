package com.sportArea.controller;

import com.sportArea.entity.MailSubscription;
import com.sportArea.entity.dto.logger.GeneralLogg;
import com.sportArea.service.EmailService;
import com.sportArea.service.MailSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {

    private final GeneralLogg generalLogg;

    private final EmailService emailService;

    private final MailSubscriptionService mailSubscriptionService;

    @PostMapping("/subscription")
    public ResponseEntity<?> sendAfterSubscription(@RequestBody MailSubscription mailRequest) throws MessagingException, IOException {

        mailSubscriptionService.addNewSubscription(mailRequest);

        generalLogg.getLoggerControllerInfo("EmailController",
                "sendAfterSubscription",
                "/send/subscription",
                "message (The subscription was successful.) and Save new subscription with user email: " + mailRequest.getEmail());

        return ResponseEntity.ok("The subscription was successful.");
    }
}
