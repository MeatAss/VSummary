package Com.VSummary.domain.entities.MySQL;

import lombok.Data;

import javax.persistence.*;

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
}
