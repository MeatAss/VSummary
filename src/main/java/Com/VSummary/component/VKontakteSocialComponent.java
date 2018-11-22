package Com.VSummary.component;

import Com.VSummary.domain.entities.VKontakteUser;
import Com.VSummary.domain.enums.AuthorityType;
import Com.VSummary.domain.enums.Role;
import Com.VSummary.domain.interfaces.OAuth2Social;
import Com.VSummary.repository.VKontakteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.vkontakte.connect.VKontakteConnectionFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Component
public class VKontakteSocialComponent implements OAuth2Social<VKontakteUser> {

    @Value("${spring.social.vkontakte.appId}")
    private String vKontakteAppId;
    @Value("${spring.social.vkontakte.appSecret}")
    private String vKontakteSecret;

    @Autowired
    private VKontakteUserRepository vKontakteUserRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public VKontakteUser activeUser(String code) {
        VKontakteConnectionFactory connectionFactory = new VKontakteConnectionFactory(vKontakteAppId, vKontakteSecret);
        AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(
                code,
                "http://localhost:8080/activate/OAuth2/vkontakte",
                null);

        String accessToken = accessGrant.getAccessToken();

        if (StringUtils.isEmpty(accessToken))
            return null;


        String uri = String.format("https://api.vk.com/method/users.get?access_token=%s&v=5.87", accessToken);

        RestTemplate restTemplate = new RestTemplate();
        HashMap<String, ArrayList<Map>> answer = restTemplate.getForObject(uri, new HashMap<>().getClass());

        Map response = answer.get("response").get(0);
        VKontakteUser vKontakteUser = vKontakteUserRepository.findByUserId(response.get("id").toString());

        if (vKontakteUser != null) {
            vKontakteUser.setToken(accessToken);
            return vKontakteUser;
        }

        vKontakteUser = new VKontakteUser(
                response.get("id").toString(),
                response.get("first_name").toString(),
                response.get("last_name").toString(),
                accessToken
        );

        vKontakteUser.randomUserData(passwordEncoder, Collections.singleton(Role.USER), AuthorityType.VKONTAKTE);
        vKontakteUserRepository.save(vKontakteUser);

        return vKontakteUser;
    }

    @Override
    public String getAuthenticationUrl() {
        VKontakteConnectionFactory vKontakteConnectionFactory = new VKontakteConnectionFactory(
                vKontakteAppId,
                vKontakteSecret);

        OAuth2Operations oauthOperations = vKontakteConnectionFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();
        params.setScope("public_profile, email");
        params.setRedirectUri("http://localhost:8080/activate/OAuth2/vkontakte");



        return oauthOperations.buildAuthorizeUrl(params) + "&v=5.87";
    }

    @Override
    public boolean instanseOf(Class cls) {
        return cls == OAuth2Social.class;
    }
}
