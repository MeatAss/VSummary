package Com.VSummary.component;

import Com.VSummary.domain.entities.SimpleUser;
import Com.VSummary.domain.enums.Role;
import Com.VSummary.domain.interfaces.SimpleAuthentication;
import Com.VSummary.repository.SimpleUserRepository;
import Com.VSummary.service.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Component
public class SimpleSocialComponent implements SimpleAuthentication {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private SimpleUserRepository simpleUserRepository;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean resiveRegistrationData(String code) {
        SimpleUser user = simpleUserRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        user.setActive(true);

        simpleUserRepository.save(user);

        return true;
    }

    @Override
    public boolean sendRegistrationData(String email, String username, String password) {
        SimpleUser userDB = simpleUserRepository.findByUsername(username);

        if (userDB != null)
            return false;

        SimpleUser user = new SimpleUser();

        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder, password);

        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());

        simpleUserRepository.save(user);

        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to vSummary. To continue, please, follow the link: http://127.0.0.1:8080/activate/Simple/simple?code=%s",
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

    @Override
    public String getAuthenticationUrl() {
        return "http://localhost:8080/login";
    }

    @Override
    public boolean instanseOf(Class cls) {
        return cls == SimpleAuthentication.class;
    }
}
