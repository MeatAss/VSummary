package Com.VSummary.domain.entities.MySQL;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comments {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name = "message", nullable = false, length = 500)
    private String message;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id", nullable = false)
    private Set<Likes> likes = new TreeSet<>();

    @Column(name = "comment_timestamp", nullable = false)
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public Comments(String message, User user) {
        this.message = message;
        this.user = user;
    }
}
