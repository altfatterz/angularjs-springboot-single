package demo.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@Configuration
public class TextEncryptorConfiguration {

    @Value("${reset.password.encryptor.password}")
    private String password;

    @Value("${reset.password.encryptor.salt}")
    private String salt;

    @Bean
    TextEncryptor textEncryptor() {
        return Encryptors.queryableText(password, salt);
    }


}
