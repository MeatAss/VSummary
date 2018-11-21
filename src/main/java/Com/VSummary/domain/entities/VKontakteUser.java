package Com.VSummary.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "vkontakte_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VKontakteUser extends User {
    @ Column(name = "user_id", length = 50)
    private String userId;

    @ Column(name = "first_name", length = 50)
    private String firstName;

    @ Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "token", length = 200)
    private String token;
}
