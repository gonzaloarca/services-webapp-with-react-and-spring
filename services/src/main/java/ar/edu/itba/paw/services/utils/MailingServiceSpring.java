package ar.edu.itba.paw.services.utils;

import ar.edu.itba.paw.interfaces.services.MailingService;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.HashMap;

@Service
public class MailingServiceSpring implements MailingService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    @Qualifier("contractEmail")
    private SimpleMailMessage contractEmail;

    @Autowired
    @Qualifier("contractEmailWithImage")
    private SimpleMailMessage contractEmailWithImage;

    private final HashMap<String, String> IMAGE_TYPE_TO_NAME = new HashMap<String, String>() {{
        put("image/png", "image.png");
        put("image/jpeg", "image.jpg");
    }};

    @Override
    public void sendHtmlMessage(String to, String subject, String html) {
        sendHtmlMessageWithAttachment(to, subject, html, null);
    }

    @Override
    public void sendHtmlMessageWithAttachment(String to, String subject, String html, DataSource attachment) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true,"utf-8");
            helper.setText(html, true);
            helper.setTo(to);
            helper.setSubject(subject);
            if (attachment != null)
                helper.addAttachment(IMAGE_TYPE_TO_NAME.get(attachment.getContentType()), attachment);
        }catch (MessagingException e){
            //TODO: PONER EXCEPCION ADECUADA
            throw new RuntimeException();
        }
        emailSender.send(mimeMessage);
    }

    @Override
    public void sendContractEmail(JobContract jobContract, JobPackage jobPack, JobPost jobPost) {
        //TODO: i18n del email
        String  clientName = jobContract.getClient().getUsername(),
                subject = clientName + " quiere contratar tu servicio",
                text;
        SimpleMailMessage message;
        DataSource attachment = null;
        byte[] imageData = jobContract.getImageData();

        if(imageData != null) {
            message = contractEmailWithImage;
            attachment = new ByteArrayDataSource(imageData, getImageType(imageData));
        } else {
            message = contractEmail;
        }

        text = String.format(message.getText(), jobPost.getUser().getUsername(),
                jobPack.getTitle(), jobPack.getPrice(), jobPost.getTitle(),
                clientName, jobContract.getClient().getEmail(), jobContract.getClient().getPhone(),
                jobContract.getDescription());

        sendHtmlMessageWithAttachment(jobPost.getUser().getEmail(), subject, text, attachment);
    }

    //TODO: decidir si seguir utilizando este metodo o agregar el imageType al Model
    private String getImageType(byte[] imageData) {
        if(imageData == null)
            throw new RuntimeException("Parameter can't be null");

        InputStream is = new BufferedInputStream(new ByteArrayInputStream(imageData));
        String mimeType;
        try {
            mimeType = URLConnection.guessContentTypeFromStream(is);
        } catch (IOException e) {
            //TODO: decidir si es mejor otro tipo de excepción
            throw new RuntimeException(e.getMessage());
        }

        return mimeType;
    }

    /*
    @Override
    public void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @Override
    public void sendTemplatedHTMLMessage(String to, String subject, Object... templateArgs){
        String text = String.format(template.getText(), templateArgs);
        sendHtmlMessage(to,subject,text);
    }

    @Override
    public void sendTemplatedMessage(String to, String subject, Object... templateArgs){
        String text = String.format(template.getText(), templateArgs);
        sendSimpleMessage(to,subject,text);
    }
    */

}
