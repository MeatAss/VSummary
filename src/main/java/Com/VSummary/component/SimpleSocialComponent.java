package Com.VSummary.component;

import Com.VSummary.domain.entities.MySQL.SimpleUser;
import Com.VSummary.domain.enums.AuthorityType;
import Com.VSummary.domain.enums.Role;
import Com.VSummary.domain.interfaces.SimpleAuthentication;
import Com.VSummary.repository.SimpleUserRepository;
import Com.VSummary.service.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public boolean receiveRegistrationData(String code) {
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
        SimpleUser userDB = simpleUserRepository.findByLogin(username);

        if (userDB != null || StringUtils.isEmpty(email))
            return false;

        SimpleUser user = configureUser(email, username, password);

        simpleUserRepository.save(user);

        sendMail(user);

        return true;
    }

    private void sendMail(SimpleUser user){
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

    private SimpleUser configureUser(String email, String username, String password) {
        SimpleUser user = new SimpleUser();

        user.setEmail(email);
        user.setLogin(username);
        user.setGivenName(username);
        user.setPassword(password);

        user.setEnable(true);
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setTypes(Collections.singleton(AuthorityType.SIMPLE));

        return user;
    }

    @Override
    public String getAuthenticationUrl() {
        return "http://localhost:8080/login";
    }

    @Override
    public boolean instanceOf(Class cls) {
        return cls == SimpleAuthentication.class;
    }
}
