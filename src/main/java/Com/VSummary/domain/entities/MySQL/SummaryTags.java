package Com.VSummary.domain.entities.MySQL;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tags", uniqueConstraints = {@UniqueConstraint(columnNames = {"tag"})})
@Data
public class SummaryTags {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "tag", length = 50)
    private String tag;

    public SummaryTags() {
    }

    public SummaryTags(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SummaryTags that = (SummaryTags) o;
        return Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag);
    }
}
