package com.JoaoLucas.Sistema.Barbearia.service.email_service;

import com.JoaoLucas.Sistema.Barbearia.service.BarbeiroService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender emailSender;

    public void enviarEmail(String to, String subject, String text) {
        log.info("Enviando email para: {}", to);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("seu-email@gmail.com");
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
        log.info("Email enviado com sucesso para: {}", to);
    }
}
