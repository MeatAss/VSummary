package Com.VSummary.domain.entities.MySQL;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "simple_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUser extends User {
    @Column(name = "email", length = 64)
    private String email;

    @Column(name = "activation_code", length = 50)
    private String activationCode;
}
