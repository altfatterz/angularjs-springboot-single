package demo.mail;

import demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1")
public class ChangePasswordController {

    private final UserRepository userRepository;
    private final ResetPasswordTokenHandler resetPasswordTokenHandler;

    @Autowired
    ChangePasswordController(UserRepository userRepository, ResetPasswordTokenHandler resetPasswordTokenHandler) {
        this.userRepository = userRepository;
        this.resetPasswordTokenHandler = resetPasswordTokenHandler;
    }

    @RequestMapping("/users/password/edit")
    public String check(@RequestParam("reset_password_token") String resetPasswordToken) {
        final UsernameDateTime usernameDateTime = resetPasswordTokenHandler.decrypt(resetPasswordToken);
        return usernameDateTime.getUsername() + "---" + usernameDateTime.getLocalDateTime();
    }

    @RequestMapping(value = "/users/password/edit", method = RequestMethod.POST)
    public String change(@RequestParam String resetPasswordToken) {

        return "unimplemented";
    }

    private String getPasswordResetToken(String username) {
        return resetPasswordTokenHandler.encrypt(username);
    }





}
