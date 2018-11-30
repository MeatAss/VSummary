package Com.VSummary.component;

import Com.VSummary.domain.entities.MySQL.FacebookUser;
import Com.VSummary.domain.interfaces.OAuth2Social;
import Com.VSummary.repository.FacebookUserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Component;

@Component
public class FacebookSocialComponent implements OAuth2Social<FacebookUser> {

    @Value("${spring.social.facebook.appId}")
    private String facebookAppId;
    @Value("${spring.social.facebook.appSecret}")
    private String facebookSecret;

    @Autowired
    private FacebookUserRepository facebookUserRepository;

    @Override
    public String getAuthenticationUrl() {
        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookSecret);
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();
        params.setRedirectUri("http://localhost:8080/activate/OAuth2/facebook");
        params.setScope("public_profile");
        return oauthOperations.buildAuthorizeUrl(params);
    }

    @Override
    public FacebookUser activeUser(String code) {
        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookSecret);
        AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(code, "http://localhost:8080/activate/OAuth2/facebook", null);
        String accessToken = accessGrant.getAccessToken();

        if (StringUtils.isEmpty(accessToken))
            return null;

        Facebook facebook = new FacebookTemplate(accessToken);
        User userData = facebook.fetchObject("me", User.class, "first_name, last_name");


        FacebookUser facebookUser = facebookUserRepository.findByUserId(userData.getId());

        if (facebookUser != null) {
            facebookUser.setToken(accessToken);

            return facebookUser;
        }

        facebookUser = new FacebookUser(
                userData.getId(),
                accessToken,
                userData.getFirstName(),
                userData.getLastName()
        );

        facebookUserRepository.save(facebookUser);

        return facebookUser;
    }

    @Override
    public boolean instanceOf(Class cls) {
        return cls == OAuth2Social.class;
    }
}