package com.example.myapp.springboot.controller;

import com.example.myapp.springboot.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-email")
    public String sendMail() {
        emailService.sendSimpleEmail(
                "recipient@example.com",
                "Test Subject",
                "This is a test email from Spring Boot.");
        return "Email Sent!";
    }
}

