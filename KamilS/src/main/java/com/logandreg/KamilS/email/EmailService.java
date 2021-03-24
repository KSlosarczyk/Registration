package com.logandreg.KamilS.email;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

    private final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);
    private final JavaMailSender javaMailSender;

    @Override
    public void send(String to, String email) {

    }
}
