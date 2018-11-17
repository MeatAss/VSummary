package Com.VSummary.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "summaryTags")
public class SummaryTags {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String tag;

    public SummaryTags() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
