package com.wanlab.mailservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailPayload {
    private String username;
    private String email;
}
