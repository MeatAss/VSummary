package Com.VSummary.domain.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "facebook_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacebookUser extends User {
    @ Column(name = "first_name", length = 50)
    private String firstName;

    @ Column(name = "last_name", length = 50)
    private String lastName;

    @ Column(name = "email", length = 64)
    private String email;

    @Column(name = "token", length = 200)
    private String token;
}
