package demo.mail;

public interface ResetPasswordTokenHandler {

    UsernameDateTime decrypt(String encryptedToken);

    String encrypt(String username);

}
