package demo.mail;

public class ResetPasswordLinkExpiredException extends Exception {

    public ResetPasswordLinkExpiredException(String message) {
        super(message);
    }
}
