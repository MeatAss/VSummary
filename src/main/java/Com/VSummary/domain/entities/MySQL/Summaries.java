package Com.VSummary.domain.entities.MySQL;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "summaries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Summaries implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "name_summary", length = 100)
    private String nameSummary;

    @Column(name = "short_description", length = 100)
    private String shortDescription;

    @Column(name = "specialty_number", length = 50)
    private String specialtyNumber;

    @Column(name = "summary_tags")
    @ElementCollection(targetClass = SummaryTags.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "summary_tags", joinColumns = @JoinColumn(name = "tag_id"))
    @Enumerated(EnumType.ORDINAL)
    private List<SummaryTags> summaryTags;

    @Lob
    @Column(name = "activation_code", length = 50)
    private String textSummary;

    @Transient
    public String getStringSummaryTags() {
        return summaryTags.stream().map(tag -> tag.getTag()).collect(Collectors.joining(" "));
    }
}
