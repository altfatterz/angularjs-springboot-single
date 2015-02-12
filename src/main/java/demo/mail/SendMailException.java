package demo.mail;

public class SendMailException extends Exception {

    public SendMailException(Throwable cause) {
        super(cause);
    }

    public SendMailException(String message) {
        super(message);
    }

}