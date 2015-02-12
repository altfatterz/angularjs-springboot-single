package demo.mail.sendgrid;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import demo.mail.MailingService;
import demo.mail.SendMailException;
import demo.mail.SendMailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SendGridMailingService implements MailingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendGridMailingService.class);

    private SendGrid sendGrid;

    @Value("${passwordrecovery.email.from:beheer@elerna.com}")
    private String from;

    @Value("${passwordrecovery.email.bcc:beheer@elerna.com}")
    private String bcc;

    @Autowired
    SendGridMailingService(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    @Override
    public void send(SendMailRequest sendMailRequest) throws SendMailException {

        LOGGER.info("Sending password recovery email to {} ...", sendMailRequest.getTo());
        try {
            SendGrid.Response response = sendGrid.send(toSendGridEmail(sendMailRequest));
            if (response.getCode() != HttpStatus.OK.value()) {
                throw new SendMailException(response.getMessage());
            }
        } catch (SendGridException e) {
            throw new SendMailException(e);
        }
    }

    SendGrid.Email toSendGridEmail(SendMailRequest sendMailRequest) {
        return new SendGrid.Email()
                .setFrom(from)
                .setBcc(new String[] {bcc})
                .setTo(new String[] {sendMailRequest.getTo()})
                .setSubject(sendMailRequest.getSubject())
                .setText(sendMailRequest.getText());

    }
}
