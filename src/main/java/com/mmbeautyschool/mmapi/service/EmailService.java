package com.mmbeautyschool.mmapi.service;

import com.mmbeautyschool.mmapi.entity.Email;

public interface EmailService {

    void sendSimpleMail(Email details);

    String sendMailWhithAttachment(Email details);

//    void sendMailHTMLFormat(Email email) throws MessagingException;
}
