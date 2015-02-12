package demo.mail;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;

public class PasswordRecoveryApiRequest {

    @Email
    private final String email;

    @JsonCreator
    public PasswordRecoveryApiRequest(@JsonProperty("email") String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }



}
