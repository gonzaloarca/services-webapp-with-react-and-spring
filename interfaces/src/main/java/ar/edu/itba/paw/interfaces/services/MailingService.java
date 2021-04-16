package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;

import javax.activation.DataSource;

public interface MailingService {

    void sendHtmlMessage(String to, String subject, String html);

    void sendHtmlMessageWithAttachment(String to, String subject, String html, DataSource attachment);

    void sendContractEmail(JobContract jobContract, JobPackage jobPack, JobPost jobPost);

    /*
    void sendSimpleMessage(String to,String subject, String text);

    void sendTemplatedMessage(String to, String subject, Object... templateArgs);

    void sendTemplatedHTMLMessage(String to, String subject, Object... templateArgs);
     */
}
