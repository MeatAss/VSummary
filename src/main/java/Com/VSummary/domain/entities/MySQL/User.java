package Com.VSummary.domain.entities.MySQL;

import Com.VSummary.domain.enums.AuthorityType;
import Com.VSummary.domain.enums.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name= "users")
@Inheritance(
        strategy = InheritanceType.JOINED
)
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable, UserDetails {
    @Transient
    public static final int nameMaxLength = 50;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(name = "given_name", length = nameMaxLength)
    private String givenName;

    @Column(name = "second_name", length = nameMaxLength)
    @Getter(AccessLevel.NONE)
    private String secondName;

    @Column(name = "user_login", length = 50)
    private String login;

    @Column(name = "password", length = 100)
    @Setter(AccessLevel.NONE)
    private String password;

    @Column(name = "active")
    private boolean active;

    @Column(name = "user_enable")
    private boolean enable;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.ORDINAL)
    private Set<Role> roles;

    @ElementCollection(targetClass = AuthorityType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_type", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.ORDINAL)
    private Set<AuthorityType> types;

    public User(String givenName, String secondName, Set<AuthorityType> types) {
        this.enable = true;
        this.givenName = givenName;
        this.secondName = secondName;
        this.types = types;
        roles = Collections.singleton(Role.USER);
    }

    public String getSecondName(){
        return secondName != null ? secondName : "";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return login;
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
        return enable;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
