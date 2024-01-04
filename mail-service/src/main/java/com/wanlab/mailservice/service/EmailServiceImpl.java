package com.wanlab.mailservice.service;

import com.wanlab.mailservice.model.EmailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailPort{
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(EmailModel emailModel) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("bigwanga13@gmail.com");
        message.setTo(emailModel.getTo());
        message.setSubject(emailModel.getSubject());
        message.setText(emailModel.getBody());
        javaMailSender.send(message);

    }
}
