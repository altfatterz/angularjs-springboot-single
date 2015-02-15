package demo.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ResetPasswordTokenHandlerImpl implements ResetPasswordTokenHandler {

    private final TextEncryptor textEncryptor;
    private final String USERNAME_DATE_TIME_SEPARATOR = "&";
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    @Autowired
    ResetPasswordTokenHandlerImpl(TextEncryptor textEncryptor) {
        this.textEncryptor = textEncryptor;
    }

    @Override
    public UsernameDateTime decrypt(String encryptedToken) {
        String[] tokens = textEncryptor.decrypt(encryptedToken).split(USERNAME_DATE_TIME_SEPARATOR);
        return new UsernameDateTime(tokens[0], LocalDateTime.parse(tokens[1], DATE_TIME_FORMATTER));
    }

    @Override
    public String encrypt(String username) {
        return textEncryptor.encrypt(username + USERNAME_DATE_TIME_SEPARATOR + LocalDateTime.now().format(DATE_TIME_FORMATTER));
    }
}
