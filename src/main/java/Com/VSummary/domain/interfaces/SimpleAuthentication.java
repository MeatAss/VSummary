package Com.VSummary.domain.interfaces;

public interface SimpleAuthentication extends SocialAuthentication {
    boolean sendRegistrationData(String email, String username, String password);
    boolean resiveRegistrationData(String code);
}
