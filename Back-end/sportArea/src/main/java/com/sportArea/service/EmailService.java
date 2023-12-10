package com.sportArea.service;


import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {

    void sendSimpleMailMessage(String name, String to);
    void sendMimeMessageWithAttachments(String name, String to) throws MessagingException, IOException;
    void sendMimeMessageWithEmbeddedFiles(String name, String to);
    void sendHtmlEmailRegistration(String to) throws MessagingException, IOException;
    void sendHtmlEmailSubscription(String to) throws MessagingException, IOException;
    void sendHtmlEmailWithEmbeddedFiles(String name, String to);

//    void sendMailSubscription(String toEmail ) throws MessagingException, IOException;
//
//    void sendMailRegistration(String toEmail, String userName) throws MessagingException, IOException;
//
//    void sendMail(String subject, String htmlName, String toEmail, String userName) throws MessagingException, IOException;

}
