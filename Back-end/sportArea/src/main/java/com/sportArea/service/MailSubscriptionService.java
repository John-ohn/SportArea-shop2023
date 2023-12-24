package com.sportArea.service;

import com.sportArea.entity.MailSubscription;

import javax.mail.MessagingException;
import java.io.IOException;

public interface MailSubscriptionService {

     void addNewSubscription(MailSubscription email) throws MessagingException, IOException;

     Boolean existsByEmail(String email);


}
