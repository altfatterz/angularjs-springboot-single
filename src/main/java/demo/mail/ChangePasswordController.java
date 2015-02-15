package demo.mail;

import demo.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/v1")
public class ChangePasswordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangePasswordController.class);

    private final UserRepository userRepository;
    private final ResetPasswordTokenHandler resetPasswordTokenHandler;
    private final int TIME_LIMIT_IN_MINUTES = 15;

    @Autowired
    ChangePasswordController(UserRepository userRepository, ResetPasswordTokenHandler resetPasswordTokenHandler) {
        this.userRepository = userRepository;
        this.resetPasswordTokenHandler = resetPasswordTokenHandler;
    }

    @RequestMapping("/users/password/edit")
    public ResponseEntity<Void> check(@RequestParam("reset_password_token") String resetPasswordToken) throws ResetPasswordLinkExpiredException{
        if (!isTokenExpired(resetPasswordToken)) {
            throw new ResetPasswordLinkExpiredException("expired reset password link");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();

    }

    @RequestMapping(value = "/users/password/edit", method = RequestMethod.POST)
    public ResponseEntity<Void> change(@RequestParam("reset_password_token") String resetPasswordToken, @RequestBody ChangePasswordApiRequest request) throws ResetPasswordLinkExpiredException {
        if (!isTokenExpired(resetPasswordToken)) {
            throw new ResetPasswordLinkExpiredException("expired reset password link");
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private boolean isTokenExpired(String resetPasswordToken) {
        final UsernameDateTime usernameDateTime = resetPasswordTokenHandler.decrypt(resetPasswordToken);
        if (LocalDateTime.now().isAfter(usernameDateTime.getLocalDateTime().plusMinutes(TIME_LIMIT_IN_MINUTES))) {
            LOGGER.warn("expired reset password link for {}", usernameDateTime.getUsername());
            return false;
        }
        return true;
    }





}
