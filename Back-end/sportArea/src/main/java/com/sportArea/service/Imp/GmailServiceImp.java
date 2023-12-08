package com.sportArea.service.Imp;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import com.sportArea.service.EmailService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.nio.charset.StandardCharsets;;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Set;

@Service
public class GmailServiceImp implements EmailService {


    private String myEmail = "jj3564527@gmail.com";
    private Gmail service;


    GmailServiceImp() throws Exception {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        service = new Gmail.Builder(httpTransport, jsonFactory, getCredentials(httpTransport, jsonFactory))
                .setApplicationName("Test Gmail")
                .build();
    }

    private static Credential getCredentials(final NetHttpTransport httpTransport, GsonFactory jsonFactory)
            throws IOException {
        // Load client secrets.
        InputStream in = GmailServiceImp.class
                .getResourceAsStream(
                        "/client_secret_7205473346-qhl974tr2fcvus5ghg9e0rngh6rl0kkq.apps.googleusercontent.com.json"
                );
        if (in == null) {
            throw new FileNotFoundException("Resource not found: ");
        }

        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets, Set.of(GmailScopes.GMAIL_SEND))
                .setDataStoreFactory(new FileDataStoreFactory(Paths.get("tokens").toFile()))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        //returns an authorized Credential object.
        return credential;
    }

    @Override
    public void sendMailSubscription(String toEmail) throws MessagingException, IOException {
        String subject = "Sport Area підписка.";
        String htmlName = "subscription";

        sendMail(subject, htmlName, toEmail, null);
    }

    @Override
    public void sendMailRegistration(String toEmail, String userName) throws MessagingException, IOException {
        String subject = "Дякуємо за реєстрацію в магазині Sport Are.";
        String htmlName = "registration";

        sendMail(subject, htmlName, toEmail, userName);
    }

    public void sendMail(String subject, String htmlName, String toEmail, String userName) throws MessagingException, IOException {

        // Encode as MIME message
        MimeMessage email = createMimeMessage(subject, htmlName, toEmail, userName);

        // Encode and wrap the MIME message into a gmail message
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
        Message message = new Message();
        message.setRaw(encodedEmail);

        try {

            // Create send message
            message = service.users().messages().send("me", message).execute();
            System.out.println("Message id: " + message.getId());
            System.out.println(message.toPrettyString());

        } catch (GoogleJsonResponseException e) {

            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 403) {
                System.err.println("Unable to send message: " + e.getDetails());
            } else {
                throw e;
            }
        }

    }

    // Create MimeMessage. Check userName and use correct Overloading method convertHtmlFileToString.
    private MimeMessage createMimeMessage(String subject, String htmlName, String toEmail, String userName) throws MessagingException, IOException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(myEmail));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(toEmail));
        email.setSubject(subject, "UTF-8");
        if (userName != null) {
            // Read the HTML content from a file or store it as a string
            String htmlContent = convertHtmlFileToString(htmlName + ".html", userName);
            email.setText(htmlContent, "utf-8", "html");
            return email;
        }
        // Read the HTML content from a file or store it as a string
        String htmlContent = convertHtmlFileToString(htmlName + ".html");
        email.setText(htmlContent, "utf-8", "html");
        return email;
    }

    // Overloading the method without replacing the values in the html file
    public static String convertHtmlFileToString(String filePath) throws IOException {
        // Load the HTML file as a resource
        ClassPathResource resource = new ClassPathResource(filePath);

        // Get the InputStream from the resource
        try (InputStream inputStream = resource.getInputStream()) {
            // Read the contents of the InputStream into a String using StreamUtils

            return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        }
    }

    // Overloading the method with replacing the values in the html file
    public static String convertHtmlFileToString(String filePath, String name) throws IOException {
        // Load the HTML file as a resource
        ClassPathResource resource = new ClassPathResource(filePath);

        // Get the InputStream from the resource
        try (InputStream inputStream = resource.getInputStream()) {
            // Read the contents of the InputStream into a String using StreamUtils

            String streamUtils = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            streamUtils = streamUtils.replace("${name}", name);

            return streamUtils;
        }
    }

}
