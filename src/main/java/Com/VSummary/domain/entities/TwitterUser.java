package Com.VSummary.domain.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "twitter_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TwitterUser extends User {
    @Column(name = "profile_id")
    private long profileId;

    @Column(name = "screen_name", length = 64)
    private String screenName;

    @ Column(name = "oauth_token", length = 80)
    private String oauthToken;

    @ Column(name = "oauth_token_secret", length = 80)
    private String oauthTokenSecret;
}
