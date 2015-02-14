package demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserLoader {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void initDatabase() {

        userRepository.deleteAll();

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setUsername("altfatterz@gmail.com");
        user.setPassword(passwordEncoder.encode("secret"));
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.addRole("ROLE_USER");
        user.addRole("ROLE_ADMIN");

        userRepository.save(user);

    }
}
