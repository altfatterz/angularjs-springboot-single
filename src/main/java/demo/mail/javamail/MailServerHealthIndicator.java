package demo.mail.javamail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.Properties;

@Component
public class MailServerHealthIndicator  extends AbstractHealthIndicator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailServerHealthIndicator.class);

    private final MailProperties mailProperties;

    @Autowired
    MailServerHealthIndicator(MailProperties mailProperties) {
        this.mailProperties = mailProperties;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        Properties properties = new Properties();
        properties.putAll(mailProperties.getProperties());

        Session session = Session.getInstance(properties, null);
        try {
            Transport transport = session.getTransport(JavaMailSenderImpl.DEFAULT_PROTOCOL);
            transport.connect(mailProperties.getHost(), mailProperties.getPort(), mailProperties.getUsername(), mailProperties.getPassword());
            transport.close();

            builder.up();
        } catch (MessagingException e) {
            LOGGER.error("JavaMail connection is down", e);
            builder.down(e);
        }
    }

}
