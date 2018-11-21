package Com.VSummary.domain.entities;

import Com.VSummary.domain.enums.AuthorityType;
import Com.VSummary.domain.enums.Role;
import lombok.*;

import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name= "users")
@Inheritance(
        strategy = InheritanceType.JOINED
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @ Column(name = "user_name", length = 50)
    private String username;

    @ Column(name = "password", length = 100)
    @Setter(AccessLevel.NONE)
    private String password;

    @ Column(name = "active")
    private boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.ORDINAL)
    private Set<Role> roles;

    @ Enumerated(EnumType.STRING)
    @ Column(name = "type")
    private AuthorityType type;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public void setPassword(PasswordEncoder passwordEncoder, String password) {
        this.password = passwordEncoder.encode(password);
    }

    public void randomUserData(PasswordEncoder passwordEncoder, Set<Role> roles, AuthorityType type) {
        this.setActive(true);
        this.setUsername(UUID.randomUUID().toString());
        this.setPassword(passwordEncoder, UUID.randomUUID().toString());
        this.setRoles(roles);
        this.setType(type);
    }
}
