package utils;

import ar.edu.itba.paw.interfaces.services.MailingService;
import ar.edu.itba.paw.services.utils.MailingServiceSpring;
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
    private final MailingServiceSpring mailingService = new MailingServiceSpring();

    @Mock
    private JavaMailSender mailSender;

    @Test
    public void testSendSimpleMail() {
        //TODO: implementar testing del MailingService
    }
}
