package demo.mail;

import demo.user.User;
import demo.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/v1")
public class PasswordRecoveryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordRecoveryController.class);

    @Autowired
    private ResetPasswordLinkGenerator resetPasswordLinkGenerator;

    @Autowired
    private MailingService mailingService;

    @Autowired
    private UserRepository userRepository;

    private final String EMAIL_SUBJECT = "Elerna Password Restore Request";

    @RequestMapping(value = "/passwordRecovery", method = RequestMethod.POST)
    public ResponseEntity<Void> send(@RequestBody @Valid PasswordRecoveryApiRequest request) throws SendMailException {

        User user = userRepository.findByUsername(request.getEmail());
        if (user == null) {
            throw new SendMailException("Not registered user");
        }

        String resetPasswordLink = resetPasswordLinkGenerator.link(request.getEmail());

        SendMailRequest sendMailRequest = new SendMailRequest.Builder()
                .to(request.getEmail())
                .withSubject(EMAIL_SUBJECT)
                .withText(createEmailBody(request.getEmail(), resetPasswordLink)).build();

        mailingService.send(sendMailRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private String createEmailBody(String username, String resetPasswordLink) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hi " + username + ", \n\n");
        sb.append("Click the link below to reset your password.\n\n");
        sb.append(resetPasswordLink);
        sb.append("\n");
        sb.append("\n");
        sb.append("Once your password is reset, you will be able to login.");
        return sb.toString();
    }

}
