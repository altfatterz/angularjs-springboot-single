package demo.mail;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class ChangePasswordApiRequest {

    @NotEmpty
    private final String password;

    @JsonCreator
    public  ChangePasswordApiRequest(@JsonProperty("password") String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

}
