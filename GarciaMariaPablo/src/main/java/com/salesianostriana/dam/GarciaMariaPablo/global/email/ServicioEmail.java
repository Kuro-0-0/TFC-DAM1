package com.salesianostriana.dam.GarciaMariaPablo.global.email;

import com.salesianostriana.dam.GarciaMariaPablo.global.daos.otros.EmailDao_Contratar;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.IOException;

@Service
public class ServicioEmail {

    @Autowired
    private JavaMailSender mailSender;

    private final String  mailFrom = "cuentapablo047@gmail.com";

    public void enviarEmail(MimeMessage email) {
        mailSender.send(email);
    }


    public MimeMessage  prepararEmailContratarPlan(EmailDao_Contratar emailDao) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        String htmlTemplate, emailObjetivo = emailDao.getPlanSeleccionado();
        Context context = new Context();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();

        context.setVariable("planSeleccionado", emailObjetivo);

        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");

        templateEngine.setTemplateResolver(templateResolver);
        htmlTemplate = templateEngine.process("mail/contratarPlan.html", context);

        message.setFrom(new InternetAddress(mailFrom));
        message.setRecipients(MimeMessage.RecipientType.TO, emailDao.getEmail());
        message.setSubject("Importante: Tu plan seleccionado no está disponible temporalmente");

        message.setContent(htmlTemplate, "text/html; charset=utf-8");
        return message;

    }
}
