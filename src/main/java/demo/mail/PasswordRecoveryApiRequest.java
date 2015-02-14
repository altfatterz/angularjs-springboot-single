package demo.mail;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class PasswordRecoveryApiRequest {

    @Email @NotEmpty
    private final String email;

    @JsonCreator
    public PasswordRecoveryApiRequest(@JsonProperty("email") String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }



}
