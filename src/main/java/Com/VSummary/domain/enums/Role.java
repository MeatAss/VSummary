package Com.VSummary.domain.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMINISTRATOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
