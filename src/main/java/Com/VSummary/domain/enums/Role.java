package Com.VSummary.domain.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMINISTRATOR;

    @Override
    public String getAuthority() {
        return name();
    }

    public static boolean isExist(String role){
        for (Role type : Role.values()){
            if (type.name().equalsIgnoreCase(role))
                return true;
        }

        return false;
    }
}
