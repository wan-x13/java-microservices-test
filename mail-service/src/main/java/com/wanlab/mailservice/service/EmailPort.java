package com.wanlab.mailservice.service;

import com.wanlab.mailservice.model.EmailModel;

public interface EmailPort {
    void sendEmail(EmailModel emailModel);
}
