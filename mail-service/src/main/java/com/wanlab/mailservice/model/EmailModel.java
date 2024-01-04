package com.wanlab.mailservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailModel {
    private String to;
    private String subject;
    private String body;
}
