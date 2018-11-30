package Com.VSummary.domain.entities.MySQL;

import Com.VSummary.domain.enums.AuthorityType;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collections;

@Entity
@Table(name = "facebook_users")
@Getter
@Setter
@NoArgsConstructor
public class FacebookUser extends User {
    @ Column(name = "user_id", length = 50)
    private String userId;

    @Column(name = "token", length = 200)
    private String token;

    public FacebookUser(String userId, String token, String givenName, String secondName){
        super(givenName, secondName, Collections.singleton(AuthorityType.FACEBOOK));

        this.userId = userId;
        this.token = token;
    }
}
