package Com.VSummary.domain.interfaces;

public interface OAuth1Social<T> extends SocialAuthentication {
    T activeUser(String oauthToken, String oauthVerifier);
}
