package ar.edu.itba.paw.services.utils;

import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.MailingService;
import ar.edu.itba.paw.models.*;
import exceptions.MailCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class MailingServiceSpring implements MailingService {

    @Autowired
    private ImageService imageService;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    @Autowired
    @Qualifier("webpageUrl")
    private String webpageUrl;

    private final HashMap<String, String> IMAGE_TYPE_TO_NAME;

    public MailingServiceSpring() {
        IMAGE_TYPE_TO_NAME = new HashMap<>();
        IMAGE_TYPE_TO_NAME.put("image/png", "image.png");
        IMAGE_TYPE_TO_NAME.put("image/jpeg", "image.jpg");
        IMAGE_TYPE_TO_NAME.put("image/jpg", "image.jpg");
    }

    @Override
    public void sendHtmlMessageWithAttachment(String to, String subject, String html, DataSource attachment) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setText(html, true);
            helper.setTo(to);
            helper.setSubject(subject);
            if (attachment != null)
                helper.addAttachment(IMAGE_TYPE_TO_NAME.get(attachment.getContentType()), attachment);
        } catch (MessagingException e) {
            throw new MailCreationException();
        }
        emailSender.send(mimeMessage);
    }

    @Override
    public void sendMessageUsingThymeleafTemplate(String to, String subject, Map<String, Object> templateModel,
                                                  String templateName, DataSource attachment) {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process(templateName, thymeleafContext);

        sendHtmlMessageWithAttachment(to, subject, htmlBody, attachment);
    }

    @Async
    @Override
    public void sendContractEmail(JobContract jobContract, JobPackage jobPack, JobPost jobPost, Locale locale) {
        DataSource attachment = null;
        ByteImage image = jobContract.getImage();

        if (imageService.isValidImage(image)) {
            attachment = new ByteArrayDataSource(image.getData(), image.getType());
        }

        Map<String, Object> data = new HashMap<>();
        data.put("professional", jobPost.getUser().getUsername());
        data.put("jobPackageTitle", jobPack.getTitle());
        data.put("jobPackagePrice", String.valueOf(Math.round(jobPack.getPrice() * 100 / 100)));
        data.put("jobPackageRateType", jobPack.getRateType().getStringCode());
        data.put("jobPostTitle", jobPost.getTitle());
        data.put("client", jobContract.getClient().getUsername());
        data.put("clientEmail", jobContract.getClient().getEmail());
        data.put("clientPhone", jobContract.getClient().getPhone());
        data.put("professionalEmail", jobContract.getProfessional().getEmail());
        data.put("professionalPhone", jobContract.getProfessional().getPhone());
        data.put("contractDescription", jobContract.getDescription());
        data.put("hasAttachment", attachment != null);

        sendMessageUsingThymeleafTemplate(jobPost.getUser().getEmail(),
                messageSource.getMessage("mail.contractToPro.subject",
                        new Object[]{jobContract.getClient().getUsername()}, locale),
                data, "contractToProfessional", attachment);

        sendMessageUsingThymeleafTemplate(jobContract.getClient().getEmail(),
                messageSource.getMessage("mail.contractToClient.subject",
                        new Object[]{jobPost.getTitle()}, locale),
                data, "contractToClient", attachment);
    }

    @Async
    @Override
    public void sendVerificationTokenEmail(User user, VerificationToken token) {
        Map<String, Object> data = new HashMap<>();
        data.put("username", user.getUsername());
        data.put("email", user.getEmail());
        data.put("phone", user.getPhone());
        data.put("url", webpageUrl + "/token?user_id=" + user.getId() + "&token=" + token.getToken());

        sendMessageUsingThymeleafTemplate(user.getEmail(),
                messageSource.getMessage("mail.token.subject",
                        new Object[]{user.getUsername()}, Locale.getDefault()),
                data, "token", null);
    }

}
