package Com.VSummary.domain.entities.MySQL;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Rating {
    @Transient
    public static final int minMark = 1;

    @Transient
    public static final int maxMark = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "mark" )
    @Min(minMark)
    @Max(maxMark)
    private byte mark;

    public Rating(User user, @Min(minMark) @Max(maxMark) byte mark) {
        this.user = user;
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return Objects.equals(user, rating.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}
