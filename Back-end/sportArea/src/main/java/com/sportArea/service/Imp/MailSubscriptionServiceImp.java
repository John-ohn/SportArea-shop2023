package com.sportArea.service.Imp;

import com.sportArea.dao.MailSubscriptionRepository;
import com.sportArea.entity.MailSubscription;
import com.sportArea.entity.dto.logger.GeneralLogg;
import com.sportArea.exception.GeneralException;
import com.sportArea.service.EmailService;
import com.sportArea.service.MailSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;


@Service
@Transactional
@RequiredArgsConstructor
public class MailSubscriptionServiceImp implements MailSubscriptionService {


    private final MailSubscriptionRepository subscriptionRepository;

    private final GeneralLogg generalLogg;

    private final EmailService emailService;

    @Override
    public void addNewSubscription(MailSubscription email) throws MessagingException, IOException {
        if(email.getEmail().isEmpty()){
            generalLogg.getLoggerServiceWarn("MailSubscriptionServiceImp",
                   "addNewSubscription",
                    "Email is Empty.",
                    HttpStatus.BAD_REQUEST);

            throw new GeneralException("Email is Empty.", HttpStatus.BAD_REQUEST);
        }
        if(!existsByEmail(email.getEmail())) {
            subscriptionRepository.save(email);
            generalLogg.getLoggerServiceInfo("MailSubscriptionServiceImp",
                    "addNewSubscription",
                    "message and new save new Subscription from Data Base email: " + email.getEmail());

            emailService.sendMailSubscription(email.getEmail());
        }else {
            generalLogg.getLoggerServiceWarn("MailSubscriptionServiceImp",
                    "addNewSubscription",
                    "Email is Empty.",
                    HttpStatus.BAD_REQUEST);

            throw new GeneralException("Нou have already subscribed.", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Boolean existsByEmail(String email){

      Boolean checkExistEmail = subscriptionRepository.existsByEmail(email);
        generalLogg.getLoggerServiceInfo("MailSubscriptionServiceImp",
                "existsByEmail",
                "message and check Email Exist Boolean: "+checkExistEmail);

        return checkExistEmail;
    }



}
