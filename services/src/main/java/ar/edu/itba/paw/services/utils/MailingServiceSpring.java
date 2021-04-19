package ar.edu.itba.paw.services.utils;

import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.MailingService;
import ar.edu.itba.paw.models.ByteImage;
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
import java.util.HashMap;

@Service
public class MailingServiceSpring implements MailingService {

    @Autowired
    private ImageService imageService;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    @Qualifier("contractEmail")
    private SimpleMailMessage contractEmail;

    @Autowired
    @Qualifier("contractEmailWithImage")
    private SimpleMailMessage contractEmailWithImage;

    private final HashMap<String, String> IMAGE_TYPE_TO_NAME;

    public MailingServiceSpring() {
        IMAGE_TYPE_TO_NAME = new HashMap<>();
        IMAGE_TYPE_TO_NAME.put("image/png", "image.png");
        IMAGE_TYPE_TO_NAME.put("image/jpeg", "image.jpg");
        IMAGE_TYPE_TO_NAME.put("image/jpg", "image.jpg");
    }

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
        ByteImage image = jobContract.getImage();

        if(imageService.isValidImage(image)) {
            message = contractEmailWithImage;
            attachment = new ByteArrayDataSource(image.getData(), image.getType());
        } else {
            message = contractEmail;
        }

        text = String.format(message.getText(), jobPost.getUser().getUsername(),
                jobPack.getTitle(), jobPack.getPrice(), jobPost.getTitle(),
                clientName, jobContract.getClient().getEmail(), jobContract.getClient().getPhone(),
                jobContract.getDescription());

        sendHtmlMessageWithAttachment(jobPost.getUser().getEmail(), subject, text, attachment);
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
