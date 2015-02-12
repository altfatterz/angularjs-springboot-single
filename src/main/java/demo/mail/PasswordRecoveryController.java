package demo.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PasswordRecoveryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordRecoveryController.class);

    @Autowired
    private MailingService mailingService;

    @RequestMapping("/passwordRecovery")
    public String sendPasswordRecoveryInstructions() throws SendMailException {

        SendMailRequest sendMailRequest = new SendMailRequest.Builder().to("zoli@gmail.com").withSubject("dummy subject").withText("dummy text").build();

        mailingService.send(sendMailRequest);

        return "Dummy! Check the logs.";
    }

}
