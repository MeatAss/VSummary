package Com.VSummary.component;

import Com.VSummary.domain.entities.TwitterUser;
import Com.VSummary.domain.enums.AuthorityType;
import Com.VSummary.domain.enums.Role;
import Com.VSummary.domain.interfaces.OAuth1Social;
import Com.VSummary.repository.TwitterUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class TwitterSocialComponent implements OAuth1Social<TwitterUser> {

    @Value("${spring.social.twitter.appId}")
    private String twitterAppId;
    @Value("${spring.social.twitter.appSecret}")
    private String twitterSecret;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TwitterUserRepository twitterUserRepository;

    @Override
    public TwitterUser activeUser(String oauthToken, String oauthVerifier) {
        TwitterConnectionFactory connectionFactory =
                new TwitterConnectionFactory(twitterAppId, twitterSecret);
        OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations();

        OAuthToken requestToken = new OAuthToken(oauthToken, oauthVerifier);
        OAuthToken accessToken = oauthOperations.exchangeForAccessToken(
                new AuthorizedRequestToken(requestToken, oauthVerifier), null);

        Twitter twitter =  new TwitterTemplate(twitterAppId, twitterSecret, accessToken.getValue(), accessToken.getSecret());

        TwitterUser twitterUser = twitterUserRepository.findByProfileId(twitter.userOperations().getProfileId());

        if (twitterUser != null) {
            twitterUser.setOauthToken(accessToken.getValue());
            twitterUser.setOauthTokenSecret(accessToken.getSecret());

            return twitterUser;
        }

        twitterUser = new TwitterUser(
                twitter.userOperations().getProfileId(),
                twitter.userOperations().getScreenName(),
                accessToken.getValue(),
                accessToken.getSecret()
        );

        twitterUser.randomUserData(passwordEncoder, Collections.singleton(Role.USER), AuthorityType.TWITTER);
        twitterUserRepository.save(twitterUser);

        return twitterUser;
    }

    @Override
    public String getAuthenticationUrl() {
        TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(twitterAppId, twitterSecret);

        OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuthToken requestToken = oauthOperations.fetchRequestToken( "http://127.0.0.1:8080/activate/OAuth1/twitter", null );
        return oauthOperations.buildAuthorizeUrl(requestToken.getValue(), OAuth1Parameters.NONE );
    }
}
