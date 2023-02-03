package com.mmbeautyschool.mmapi.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Email {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
