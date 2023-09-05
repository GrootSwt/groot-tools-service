package com.groot.business.service;

public interface MailService {

    void sendSimpleMail(String to, String subject, String content);

    void sendHTMLMail(String to, String subject, String content);
}
