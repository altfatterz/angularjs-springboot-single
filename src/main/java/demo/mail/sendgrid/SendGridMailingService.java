package demo.mail.sendgrid;

import demo.mail.MailingService;
import demo.mail.SendMailException;
import demo.mail.SendMailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SendGridMailingService implements MailingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendGridMailingService.class);

    @Value("#{environment.SENDGRID_USERNAME}")
    private String username;

    @Value("#{environment.SENDGRID_PASSWORD}")
    private String password;


    @Override
    public void send(SendMailRequest sendMailRequest) throws SendMailException {


        LOGGER.info("Username:" + username);

        LOGGER.info("Password:" + password);


    }
}
