package com.suvash.chirkutt.Service;


public interface EmailService {
    boolean sendEmailToUser(String userEmail, String subject, String body);
}
