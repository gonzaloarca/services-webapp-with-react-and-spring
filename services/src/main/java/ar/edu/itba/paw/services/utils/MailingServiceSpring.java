package ar.edu.itba.paw.services.utils;

import ar.edu.itba.paw.interfaces.dao.JobContractDao;
import ar.edu.itba.paw.interfaces.dao.JobPostDao;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.MailingService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.MailCreationException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class MailingServiceSpring implements MailingService {

    @Autowired
    private ImageService imageService;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JobPostDao jobPostDao;

    @Autowired
    private JobContractDao jobContractDao;

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

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
                                                  String templateName, DataSource attachment, Locale locale) {
        Context thymeleafContext = new Context(locale);
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process(templateName, thymeleafContext);

        sendHtmlMessageWithAttachment(to, subject, htmlBody, attachment);
    }

    @Async
    @Override
    public void sendContractEmail(JobContractWithImage jobContract, Locale locale, String webpageUrl) {
        DataSource attachment = null;
        ByteImage image = jobContract.getByteImage();
        JobPackage jobPack = jobContract.getJobPackage();
        JobPost jobPost = jobPack.getJobPost();

        if (imageService.isValidImage(image))
            attachment = new ByteArrayDataSource(image.getData(), image.getType());

        User professional = jobPostDao.findUserByPostId(jobPost.getId()).orElseThrow(UserNotFoundException::new);
        User client = jobContractDao.findClientByContractId(jobContract.getId()).orElseThrow(UserNotFoundException::new);

        Map<String, Object> data = new HashMap<>();
        setContractBasicData(jobContract, jobPack, locale, attachment != null, professional, client, data);
        data.put("scheduledTime", jobContract.getScheduledDate().toLocalTime().toString().split("\\.")[0]);

        sendMessageUsingThymeleafTemplate(professional.getEmail(),
                messageSource.getMessage("mail.contractToPro.subject",
                        new Object[]{client.getUsername()}, locale),
                data, "contractToProfessional", attachment, locale);

        sendMessageUsingThymeleafTemplate(client.getEmail(),
                messageSource.getMessage("mail.contractToClient.subject",
                        new Object[]{jobPost.getTitle()}, locale),
                data, "contractToClient", attachment, locale);
    }

    @Async
    @Override
    public void sendVerificationTokenEmail(User user, VerificationToken token, Locale locale, String webpageUrl) {
        Map<String, Object> data = new HashMap<>();
        data.put("username", user.getUsername());
        data.put("email", user.getEmail());
        data.put("phone", user.getPhone());
        data.put("url", webpageUrl + "?user-id=" + user.getId() + "&token=" + token.getToken());
        sendMessageUsingThymeleafTemplate(user.getEmail(),
                messageSource.getMessage("mail.token.subject",
                        new Object[]{user.getUsername()}, locale),
                data, "token", null, locale);
    }

    @Async
    @Override
    public void sendRecoverPasswordEmail(User user, RecoveryToken token, Locale locale, String webpageUrl) {
        Map<String, Object> data = new HashMap<>();
        data.put("url", webpageUrl + "?user-id=" + user.getId() + "&token=" + token.getToken());

        sendMessageUsingThymeleafTemplate(user.getEmail(),
                messageSource.getMessage("mail.recover.subject", new Object[]{}, locale),
                data, "recoverPassword", null, locale);
    }

    @Async
    @Override
    public void sendUpdateContractStatusEmail(JobContract.ContractState newState, JobContractWithImage jobContract,
                                              JobPackage jobPack, JobPost jobPost, Locale locale, String webpageUrl) {
        DataSource attachment = null;
        ByteImage image = jobContract.getByteImage();

        if (imageService.isValidImage(image))
            attachment = new ByteArrayDataSource(image.getData(), image.getType());

        User professional = jobPostDao.findUserByPostId(jobPost.getId()).orElseThrow(UserNotFoundException::new);
        User client = jobContractDao.findClientByContractId(jobContract.getId()).orElseThrow(UserNotFoundException::new);

        Map<String, Object> data = new HashMap<>();
        setContractBasicData(jobContract, jobPack, locale, attachment != null, professional, client, data);
        data.put("scheduledTime", jobContract.getScheduledDate().toLocalTime().toString());
        data.put("status", newState.getDescription());
        data.put("modifiedState", newState == JobContract.ContractState.CLIENT_MODIFIED ||
                newState == JobContract.ContractState.PRO_MODIFIED);

        List<JobContract.ContractState> clientStates = Arrays.asList(
                JobContract.ContractState.CLIENT_CANCELLED, JobContract.ContractState.CLIENT_MODIFIED, JobContract.ContractState.CLIENT_REJECTED);

        boolean updatedByClient = clientStates.contains(newState);
        data.put("updatedByClient", updatedByClient);
        if (updatedByClient) {
            data.put("title", "mail.updateContract.newStatusByClient." + newState);
        } else {
            data.put("title", "mail.updateContract.newStatusByProfessional." + newState);
        }

        sendMessageUsingThymeleafTemplate(updatedByClient ? professional.getEmail() : client.getEmail(),
                messageSource.getMessage("mail.updateContract.subject." + newState,
                        new Object[]{
                                messageSource.getMessage("JobContract.ContractState." + newState.getDescription(), new Object[]{}, locale)
                        }, locale),
                data, "updateContractStatus", attachment, locale);
    }

    private void setContractBasicData(JobContractWithImage jobContract, JobPackage jobPack,
                                      Locale locale, Boolean hasAttachment, User professional, User client,
                                      Map<String, Object> data) {
        data.put("jobPackageTitle", jobPack.getTitle());
        Double price = jobPack.getPrice();
        if (price == null)
            price = (double) 0;

        data.put("jobPackagePrice", String.valueOf(Math.round(price * 100 / 100)));
        data.put("jobPackageRateType", "JobPackage.RateType." + jobPack.getRateType().getDescription());
        data.put("jobPostTitle", jobPack.getJobPost().getTitle());
        data.put("client", client.getUsername());
        data.put("clientEmail", client.getEmail());
        data.put("clientPhone", client.getPhone());
        data.put("professional", professional.getUsername());
        data.put("professionalEmail", professional.getEmail());
        data.put("professionalPhone", professional.getPhone());
        data.put("contractDescription", jobContract.getDescription());
        data.put("hasAttachment", hasAttachment);
        data.put("contractDate", jobContract.getCreationDate().format(
                DateTimeFormatter.ofPattern(messageSource.getMessage("date.format", new Object[]{}, locale))));
        data.put("scheduledDate", jobContract.getScheduledDate().toLocalDate().toString());
    }
}
