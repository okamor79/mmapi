package com.mmbeautyschool.mmapi.service.impl;

import com.mmbeautyschool.mmapi.entity.Email;
import com.mmbeautyschool.mmapi.service.EmailService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;


    @Value("${sender.mail}")
    private String sender;

    @Override
    public void sendSimpleMail(Email details) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(sender);
        msg.setTo(details.getRecipient());
        msg.setSubject(details.getSubject());
        msg.setText(details.getMsgBody());
        javaMailSender.send(msg);
    }

    @Override
    public String sendMailWhithAttachment(Email details) {
        return null;
    }

}