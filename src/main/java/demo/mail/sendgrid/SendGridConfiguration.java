package demo.mail.sendgrid;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendGridConfiguration {

    @Value("#{environment.SENDGRID_USERNAME}")
    private String username;

    @Value("#{environment.SENDGRID_PASSWORD}")
    private String password;

    @Bean
    public SendGrid sendGrid() {
        return new SendGrid(username, password);
    }

}
