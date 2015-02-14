package demo.mail;

import demo.user.User;
import demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
class ResetPasswordLinkGeneratorImpl implements ResetPasswordLinkGenerator {

    @Value("${server.address}")
    private String host;

    @Value("${server.port}")
    private String port;

    @Value("${reset.password.link.password}")
    private String encryptorPassword;

    @Value("${reset.password.link.password}")
    private String encryptorSecret;

    private final UserRepository userRepository;

    @Autowired
    ResetPasswordLinkGeneratorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String link(String username) {
        String link = "";
        User user = userRepository.findByUsername(username);
        if (user != null) {
            link = "http://" + host + ":" + port + "/v1/users/password/edit?reset_password_token=" + getPasswordResetToken(username);
        }
        return link;
    }


    private String getPasswordResetToken(String username) {
        TextEncryptor encryptor = Encryptors.queryableText(encryptorPassword, encryptorSecret);
        return encryptor.encrypt(username + ":" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    }

}
