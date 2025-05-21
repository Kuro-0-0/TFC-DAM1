package com.salesianostriana.dam.GarciaMariaPablo.global.email;

import com.salesianostriana.dam.GarciaMariaPablo.global.daos.otros.EmailDao_Contactar;
import com.salesianostriana.dam.GarciaMariaPablo.global.daos.otros.EmailDao_Contratar;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ServicioEmail {

    @Autowired
    private JavaMailSender mailSender;

    private final String  mailFrom = "cuentapablo047@gmail.com";

    public void enviarEmail(MimeMessage email) {
        mailSender.send(email);
    }
    public void enviarEmail(SimpleMailMessage email) {
        mailSender.send(email);
    }

    public String prepararHTML(String path, Context context) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();

        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine.process(path, context);
    }


    public MimeMessage  prepararEmailContratarPlan(EmailDao_Contratar emailDao) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        String htmlTemplate;
        Context context = new Context();
        context.setVariable("planSeleccionado", emailDao.getPlanSeleccionado());
        htmlTemplate = prepararHTML("mail/contratarPlan.html", context);

        message.setFrom(new InternetAddress(mailFrom));
        message.setRecipients(MimeMessage.RecipientType.TO, emailDao.getEmail());
        message.setSubject("Importante: Tu plan seleccionado no está disponible temporalmente");
        message.setContent(htmlTemplate, "text/html; charset=utf-8");

        return message;
    }

    public MimeMessage prepararEmailContactoCliente(EmailDao_Contactar emailDao) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        String htmlTemplate;
        Context context = new Context();

        context.setVariable("nombreCliente", emailDao.getNombre());
        context.setVariable("fechaEnvio", LocalDate.now());

        htmlTemplate = prepararHTML("mail/contactar.html", context);

        message.setFrom(new InternetAddress(mailFrom));
        message.setRecipients(MimeMessage.RecipientType.TO, emailDao.getEmail());
        message.setSubject("Confirmación de contacto - Equipo de Gestión de Incidencias");
        message.setContent(htmlTemplate, "text/html; charset=utf-8");

        return message;
    }

    public SimpleMailMessage prepararEmailContactoManagement(EmailDao_Contactar emailDao) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("pgarmar2306@gmail.com");
        mail.setFrom(mailFrom);
        mail.setSubject("Incidencia en contaco: " + emailDao.getAsunto());
        mail.setText("El cliente de nombre: " + emailDao.getNombre() + " ha enviado un mensaje al sistema de contacto " +
                "para que nos pongamos en contacto con el.\n Su email es: " + emailDao.getEmail() + " y ha enviado la " +
                "solicitud el dia y hora: " + LocalDateTime.now() + " el contenido de su mensaje era: \n " +
                "Asunto: " + emailDao.getAsunto() + "\nMensaje: " + emailDao.getMensaje());
        return mail;
    }
}
