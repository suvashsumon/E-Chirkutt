package com.suvash.chirkutt.Service.Impl;

import com.suvash.chirkutt.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    public boolean sendEmailToUser(String userEmail, String subject, String body)
    {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(userEmail);
            mail.setSubject(subject);
            mail.setText(body);
            javaMailSender.send(mail);
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
