package Com.VSummary.service;

import Com.VSummary.domain.entities.Role;
import Com.VSummary.domain.entities.User;
import Com.VSummary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailSender mailSender;


    public boolean addUser(User user) {
        User userDB = userRepository.findByUsername(user.getUsername());

        if (userDB != null)
            return false;

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());

        userRepository.save(user);

        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
              "Hello, %s! \n" +
                      "Welcome to vSummary. To continue, please, follow the link: http://127.0.0.1:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSender.send(
                    user.getEmail(),
                    "Activation code",
                    message
            );
        }


        return true;
    }

    public boolean activateUser(String code) {

        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        user.setActive(true);

        userRepository.save(user);

        return true;
    }
}
