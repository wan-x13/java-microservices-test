package com.wanlab.microapi2.service;

import com.wanlab.microapi2.model.EmailModel;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private  MailSender javaMailSender;

    @Override
    public void sendEmail(EmailModel emailModel) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailModel.getTo());
        message.setTo(emailModel.getTo());
        message.setSubject(emailModel.getSubject());
        message.setText(emailModel.getBody());

        javaMailSender.send(message);
        System.out.println(emailModel);

    }
}
