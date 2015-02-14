package demo.mail.javamail;

import demo.mail.MailingService;
import demo.mail.SendMailException;
import demo.mail.SendMailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class JavaMailMailingService implements MailingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaMailMailingService.class);

    private final MailSender mailSender;

    @Autowired
    JavaMailMailingService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(SendMailRequest sendMailRequest) throws SendMailException {
        LOGGER.info("Sending email: {}", sendMailRequest.getTo());

        mailSender.send(toMailMessage(sendMailRequest));
    }

    private SimpleMailMessage toMailMessage(SendMailRequest sendMailRequest) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(sendMailRequest.getTo());
        msg.setSubject(sendMailRequest.getSubject());
        msg.setText(sendMailRequest.getText());
        return msg;
    }
}
