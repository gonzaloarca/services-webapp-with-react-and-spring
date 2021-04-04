package utils;

import ar.edu.itba.paw.interfaces.services.MailingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

@RunWith(MockitoJUnitRunner.class)
public class MailServiceSpringTest {

    @InjectMocks
    private MailingService mailingService;

    @Mock
    private JavaMailSender mailSender;

    @Test
    public void testSendSimpleMail() {

    }
}
