package com.wanlab.authserver.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailPayload {
    private String to;
    private String subject;
    private String body;
}
