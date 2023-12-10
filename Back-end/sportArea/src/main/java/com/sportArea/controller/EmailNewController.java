package com.sportArea.controller;

import com.sportArea.entity.MailSubscription;
import com.sportArea.entity.dto.logger.GeneralLogg;
import com.sportArea.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class EmailNewController {

    private final GeneralLogg generalLogg;

    @Qualifier("gmailSMTServiceImp")
    private final EmailService emailService;

    @PostMapping("/subscription")
    public ResponseEntity<?> sendAfterSubscription(@RequestBody MailSubscription mailRequest) throws MessagingException, IOException {
        emailService.sendHtmlEmailSubscription(mailRequest.getEmail());

        generalLogg.getLoggerControllerInfo("EmailNewController",
                "sendAfterSubscription",
                "/send/subscription",
                "message (The subscription was successful.) and Save new subscription with user email: " + mailRequest.getEmail());

        return ResponseEntity.ok("The subscription was successful.");
    }

    @PostMapping("/registr")
    public ResponseEntity<?> sendAfterRegistr(@RequestBody MailSubscription mailRequest) throws MessagingException, IOException {
        emailService.sendHtmlEmailRegistration(mailRequest.getEmail());

        generalLogg.getLoggerControllerInfo("EmailNewController",
                "sendAfterRegistr",
                "/send/subscription",
                "message (The subscription was successful.) and Save new subscription with user email: " + mailRequest.getEmail());

        return ResponseEntity.ok("The subscription was successful.");
    }

}
