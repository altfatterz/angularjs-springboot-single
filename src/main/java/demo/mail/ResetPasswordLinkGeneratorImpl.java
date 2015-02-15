package demo.mail;

import demo.user.User;
import demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
class ResetPasswordLinkGeneratorImpl implements ResetPasswordLinkGenerator {

    @Value("${server.port}")
    private String port;

    private final UserRepository userRepository;
    private final ResetPasswordTokenHandler resetPasswordTokenHandler;

    @Autowired
    ResetPasswordLinkGeneratorImpl(UserRepository userRepository, ResetPasswordTokenHandler resetPasswordTokenHandler) {
        this.userRepository = userRepository;
        this.resetPasswordTokenHandler = resetPasswordTokenHandler;
    }

    @Override
    public String link(String username) {
        String link = "";
        User user = userRepository.findByUsername(username);
        if (user != null) {
            link = "http://" + "localhost" + ":" + port + "/v1/users/password/edit?reset_password_token=" + getPasswordResetToken(username);
        }
        return link;
    }


    private String getPasswordResetToken(String username) {
        return resetPasswordTokenHandler.encrypt(username);
    }

}
