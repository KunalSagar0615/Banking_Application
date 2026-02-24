package com.example.demo.email;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class EmailService {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    private static final String FROM_EMAIL = "kunals.pcimca@gmail.com";

    public void sendMail(String to, String subject, String message) {

        Email from = new Email(FROM_EMAIL);
        Email toEmail = new Email(to);
        Content content = new Content("text/html", message);

        Mail mail = new Mail(from, subject, toEmail, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            sg.api(request);

        } catch (IOException ex) {
            throw new RuntimeException("Error sending email", ex);
        }
    }
}