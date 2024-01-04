package com.wanlab.microapi2.service;

import com.wanlab.microapi2.model.EmailModel;

public interface EmailService {
    void sendEmail(EmailModel emailModel);
}
