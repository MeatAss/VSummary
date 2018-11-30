package Com.VSummary.domain.entities.MySQL;

import Com.VSummary.domain.enums.AuthorityType;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collections;

@Entity
@Table(name = "twitter_users")
@Getter
@Setter
@NoArgsConstructor
public class TwitterUser extends User {
    @Column(name = "profile_id")
    private long userId;

    @ Column(name = "oauth_token", length = 80)
    private String oauthToken;

    @ Column(name = "oauth_token_secret", length = 80)
    private String oauthTokenSecret;

    public TwitterUser(Long userId, String oauthToken, String oauthTokenSecret, String givenName){
        super(givenName, "", Collections.singleton(AuthorityType.TWITTER));

        this.userId = userId;
        this.oauthToken = oauthToken;
        this.oauthTokenSecret = oauthTokenSecret;
    }
}
