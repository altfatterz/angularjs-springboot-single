package demo.mail;

import java.time.LocalDateTime;

public class UsernameDateTime {

    private final String username;
    private final LocalDateTime localDateTime;

    public UsernameDateTime(String username, LocalDateTime localDateTime) {
        this.username = username;
        this.localDateTime = localDateTime;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
