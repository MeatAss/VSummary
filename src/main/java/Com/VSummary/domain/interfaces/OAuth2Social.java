package Com.VSummary.domain.interfaces;

public interface OAuth2Social<T> extends SocialAuthentication {
    T activeUser(String code);
}
