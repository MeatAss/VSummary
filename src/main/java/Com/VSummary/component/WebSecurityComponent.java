package Com.VSummary.component;

import Com.VSummary.domain.entities.MySQL.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class WebSecurityComponent {

    public void SignIn(User user) {
        AbstractAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                user,
                user.getRoles(),
                user.getRoles()
        );

        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
