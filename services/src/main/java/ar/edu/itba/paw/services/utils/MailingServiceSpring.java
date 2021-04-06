package ar.edu.itba.paw.services.utils;

import ar.edu.itba.paw.interfaces.services.MailingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailingServiceSpring implements MailingService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    public SimpleMailMessage template;

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
    @Override
    public void sendHtmlMessage(String to,String subject,String html) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            helper.setText(html, true);
            helper.setTo(to);
            helper.setSubject(subject);
        }catch (MessagingException e){
            //TODO: PONER EXCEPCION ADECUADA
            throw new RuntimeException();
        }
        emailSender.send(mimeMessage);

    }
    @Override
    public void sendTemplatedHTMLMessage(String to, String subject, Object... templateArgs){
        System.out.println(template.getText());
        String text = String.format(template.getText(), templateArgs);
        sendHtmlMessage(to,subject,text);
    }

    @Override
    public void sendTemplatedMessage(String to, String subject, Object... templateArgs){
        String text = String.format(template.getText(), templateArgs);
        sendSimpleMessage(to,subject,text);
    }

}
