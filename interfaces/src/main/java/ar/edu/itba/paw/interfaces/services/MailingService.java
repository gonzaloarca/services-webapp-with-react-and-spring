package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.*;

import javax.activation.DataSource;
import java.util.Map;

public interface MailingService {

    void sendHtmlMessageWithAttachment(String to, String subject, String html, DataSource attachment);

    void sendContractEmail(JobContract jobContract, JobPackage jobPack, JobPost jobPost);

    void sendVerificationTokenEmail(User user, VerificationToken token);

    void sendMessageUsingThymeleafTemplate(String to, String subject, Map<String, Object> templateModel,
                                           String templateName, DataSource attachment);

}
