package demo.mail;

public interface MailingService {

    void send(SendMailRequest sendMailRequest) throws SendMailException;

}
