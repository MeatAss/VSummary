package Com.VSummary.service;

import Com.VSummary.component.*;
import Com.VSummary.domain.entities.MySQL.User;
import Com.VSummary.domain.enums.AuthorityType;
import Com.VSummary.domain.interfaces.OAuth1Social;
import Com.VSummary.domain.interfaces.OAuth2Social;
import Com.VSummary.domain.interfaces.SimpleAuthentication;
import Com.VSummary.domain.interfaces.SocialAuthentication;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthenticationService {

    @Autowired
    WebSecurityComponent webSecurityComponent;

    @Autowired
    SimpleSocialComponent simple;

    @Autowired
    FacebookSocialComponent facebook;

    @Autowired
    VKontakteSocialComponent vkontakte;

    @Autowired
    TwitterSocialComponent twitter;

    public ResponseEntity<String> activeSimple(String type, String code) {
        SocialAuthentication socialAuthentication = checkParams(SimpleAuthentication.class, type, code);

        if (socialAuthentication == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ((SimpleAuthentication) socialAuthentication).receiveRegistrationData(code) ?
            new ResponseEntity<>(createRedirectHeader("http://localhost:8080/main"), HttpStatus.FOUND) :
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> sendSimpleUserActivationCode(String type, String email, String username, String password) {
        SocialAuthentication socialAuthentication = checkParams(SimpleAuthentication.class, type, email, username, password);

        if (socialAuthentication == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ((SimpleAuthentication) socialAuthentication).sendRegistrationData(email, username, password) ?
            new ResponseEntity<>(createRedirectHeader("http://localhost:8080/main"), HttpStatus.FOUND) :
            new ResponseEntity<>("This user is exist", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> activateOAuth1User(String type, String oauthToken, String oauthVerifier) {
        SocialAuthentication socialAuthentication = checkParams(OAuth1Social.class, type, oauthToken, oauthVerifier);

        if (socialAuthentication == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return  activateOAuth((User)((OAuth1Social) socialAuthentication).activeUser(oauthToken, oauthVerifier));
    }

    public ResponseEntity<String> activateOAuth2User(String type, String code) {
        SocialAuthentication socialAuthentication = checkParams(OAuth2Social.class, type, code);

        if (socialAuthentication == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return activateOAuth((User)((OAuth2Social) socialAuthentication).activeUser(code));
    }

    public ResponseEntity<String> openAuthentication(String type) {
        return isEmptyParams(type) ?
                new ResponseEntity<>("", HttpStatus.BAD_REQUEST)
                :
                new ResponseEntity<>(
                    new JSONObject().put(
                            "url",
                            getAuthenticationUrl(type)
                    ).toString(), HttpStatus.OK);
    }

    private SocialAuthentication checkParams(Class typeSocialAuthentication, String type, String... params){
        if (isEmptyParams(params) && checkType(type))
            return null;

        SocialAuthentication socialAuthentication = getSocialByAuthorityType(type);

        if (socialAuthentication == null)
            return null;

        return socialAuthentication.instanceOf(typeSocialAuthentication) ?
                socialAuthentication :
                null;
    }

    private String getAuthenticationUrl(String type) {
        SocialAuthentication socialAuthentication = getSocialByAuthorityType(type);
        return socialAuthentication != null ? socialAuthentication.getAuthenticationUrl() : "";
    }

    private boolean isEmptyParams(String... params){
        for (String param : params) {
            if (StringUtils.isEmpty(param))
                return true;
        }

        return false;
    }

    private HttpHeaders createRedirectHeader(String redirectURL){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URIBuilder.fromUri(redirectURL).build());

        return httpHeaders;
    }

    private SocialAuthentication getSocialByAuthorityType(String type) {
        switch (AuthorityType.valueOf(type.toUpperCase())) {
            case FACEBOOK: return facebook;
            case TWITTER: return twitter;
            case VKONTAKTE: return vkontakte;
            case SIMPLE: return simple;
            default: return null;
        }
    }

    private boolean checkType(String type){
        for (AuthorityType authorityType : AuthorityType.values()){
            if (authorityType.name().equalsIgnoreCase(type)){
                return true;
            }
        }

        return false;
    }

    private ResponseEntity<String> activateOAuth(User user) {
        if (user == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        webSecurityComponent.SignIn(user);
        return new ResponseEntity<>(createRedirectHeader("http://localhost:8080/main"), HttpStatus.FOUND);
    }
}
