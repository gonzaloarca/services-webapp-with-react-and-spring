package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.*;

import javax.activation.DataSource;
import java.util.Locale;
import java.util.Map;

public interface MailingService {

    void sendHtmlMessageWithAttachment(String to, String subject, String html, DataSource attachment);

    void sendContractEmail(JobContractWithImage jobContract, Locale locale, String webpageUrl);

    void sendVerificationTokenEmail(User user, VerificationToken token, Locale locale,String webpageUrl);

    void sendMessageUsingThymeleafTemplate(String to, String subject, Map<String, Object> templateModel,
                                           String templateName, DataSource attachment, Locale locale);

    void sendRecoverPasswordEmail(User user, RecoveryToken token, Locale locale,String webpageUrl);

    void sendUpdateContractStatusEmail(JobContract.ContractState newState, JobContractWithImage jobContract,
                                       JobPackage jobPack, JobPost jobPost, Locale locale,String webpageUrl);
}
