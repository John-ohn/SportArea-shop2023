package com.sportArea.service;


import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {


    void sendMailSubscription(String toEmail ) throws MessagingException, IOException;

    void sendMailRegistration(String toEmail, String userName) throws MessagingException, IOException;

    void sendMail(String subject, String htmlName, String toEmail, String userName) throws MessagingException, IOException;

}
