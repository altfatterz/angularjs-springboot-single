package demo.mail;

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

    @RequestMapping(value = "/passwordRecovery", method = RequestMethod.POST)
    public ResponseEntity<Void> send(@RequestBody @Valid PasswordRecoveryApiRequest request) throws SendMailException {


        LOGGER.info("password generator:" + resetPasswordLinkGenerator.link(request.getEmail()));



        SendMailRequest sendMailRequest = new SendMailRequest.Builder().to(request.getEmail()).withSubject("Test password recovery").withText("Dummy text").build();

        mailingService.send(sendMailRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
