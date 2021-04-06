package ar.edu.itba.paw.interfaces.services;

public interface MailingService {

    void sendSimpleMessage(String to,String subject, String text);

    void sendTemplatedMessage(String to, String subject, Object... templateArgs);

    void sendTemplatedHTMLMessage(String to, String subject, Object... templateArgs);

    void sendHtmlMessage(String to,String subject,String html);
}
