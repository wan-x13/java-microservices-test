package com.wanlab.mailservice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanlab.mailservice.model.EmailModel;
import com.wanlab.mailservice.model.MailPayload;
import com.wanlab.mailservice.service.EmailServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class MailListener {
    private final EmailServiceImpl emailService;
    public MailListener(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }
    public void onMessageReceived(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        MailPayload email = mapper.readValue(message, MailPayload.class);
        EmailModel emailModel = new EmailModel();
        emailModel.setBody("Hello, " + email.getUsername() + "!");
        emailModel.setTo(email.getEmail());
        emailModel.setSubject("Wanlab Mail Service");
        emailService.sendEmail(emailModel);
        System.out.println("Received message: " + message);
    }
}
