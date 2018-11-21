package Com.VSummary.service;

import Com.VSummary.component.*;
import Com.VSummary.domain.entities.User;
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

import java.net.URI;

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
        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(code))
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);

        SocialAuthentication socialAuthentication;

        try {
            AuthorityType authorityType = AuthorityType.valueOf(type.toUpperCase());
            socialAuthentication = getSocialByAuthorityType(authorityType);
        } catch (NullPointerException e) {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }

        if (!(socialAuthentication instanceof SimpleAuthentication))
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);

        if (((SimpleAuthentication) socialAuthentication).resiveRegistrationData(code))
            return new ResponseEntity<>("http://localhost:8080", HttpStatus.OK);
        else
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> sendSimpleUserActivationCode(String type, String email, String username, String password) {
        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(email) || StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);

        SocialAuthentication socialAuthentication;

        try {
            AuthorityType authorityType = AuthorityType.valueOf(type.toUpperCase());
            socialAuthentication = getSocialByAuthorityType(authorityType);
        } catch (NullPointerException e) {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }

        if (!(socialAuthentication instanceof SimpleAuthentication))
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);

        if (((SimpleAuthentication) socialAuthentication).sendRegistrationData(email, username, password))
            return new ResponseEntity<>("http://localhost:8080", HttpStatus.OK);
        else
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> activateOAuth1User(String type, String oauthToken, String oauthVerifier) {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setLocation(URIBuilder.fromUri("http://localhost:8080/main").build());

        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(oauthVerifier) || oauthToken == null)
            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);

        SocialAuthentication socialAuthentication;

        try {
            AuthorityType authorityType = AuthorityType.valueOf(type.toUpperCase());
            socialAuthentication = getSocialByAuthorityType(authorityType);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
        }

        if (!(socialAuthentication instanceof OAuth1Social))
            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);

        User user = (User)((OAuth1Social) socialAuthentication).activeUser(oauthToken, oauthVerifier);

        if (user == null)
            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);


        webSecurityComponent.SignIn(user);
        return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
    }

    public ResponseEntity<String> activateOAuth2User(String type, String code) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URIBuilder.fromUri("http://localhost:8080/main").build());

        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(code))
            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);

        SocialAuthentication socialAuthentication;

        try {
            AuthorityType authorityType = AuthorityType.valueOf(type.toUpperCase());
            socialAuthentication = getSocialByAuthorityType(authorityType);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
        }

        if (!(socialAuthentication instanceof OAuth2Social))
            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);

        User user = (User)((OAuth2Social) socialAuthentication).activeUser(code);

        if (user == null)
            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);


        webSecurityComponent.SignIn(user);
        return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
    }

    public ResponseEntity<String> openAuthentication(String type) {
        if (StringUtils.isEmpty(type))
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(
                    new JSONObject().put(
                            "url",
                            getAuthenticationUrl(AuthorityType.valueOf(type))
                    ).toString(), HttpStatus.OK);
    }

    private String getAuthenticationUrl(AuthorityType authorityType) {
        SocialAuthentication socialAuthentication = getSocialByAuthorityType(authorityType);
        return socialAuthentication != null ? socialAuthentication.getAuthenticationUrl() : "";
    }

    private SocialAuthentication getSocialByAuthorityType(AuthorityType authorityType) {
        if (authorityType == AuthorityType.SIMPLE)
            return simple;
        if (authorityType == AuthorityType.FACEBOOK)
            return facebook;
        if (authorityType == AuthorityType.VKONTAKTE)
            return vkontakte;
        if (authorityType == AuthorityType.TWITTER)
            return twitter;

        return null;
    }
}
