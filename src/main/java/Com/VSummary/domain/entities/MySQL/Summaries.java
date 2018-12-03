package Com.VSummary.domain.entities.MySQL;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;
import java.util.TreeSet;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "name_summary", length = 100, nullable = false)
    private String nameSummary;

    @Column(name = "short_description", length = 100, nullable = false)
    private String shortDescription;

    @Column(name = "specialty_number", length = 50, nullable = false)
    private String specialtyNumber;

    @Column(name = "summary_timestamp", nullable = false)
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "summary_tags",
            joinColumns = { @JoinColumn(name = "summary_id") },
            inverseJoinColumns = { @JoinColumn(name = "summary_tags") })
    private Set<SummaryTags> summaryTags = new TreeSet<>();

    @Lob
    @Column(name = "summary_text", length = 2000, nullable = false)
    private String textSummary;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "comment_id", nullable = false)
    private Set<Comments> comments = new TreeSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id", nullable = false)
    private Set<Rating> ratings = new TreeSet<>();

    @Transient
    public String getStringSummaryTags() {
        return summaryTags.stream().map(SummaryTags::getTag).collect(Collectors.joining(" "));
    }
}

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "summary_comments",
//            joinColumns = { @JoinColumn(name = "summary_id") },
//            inverseJoinColumns = { @JoinColumn(name = "comment_id") }
//    )

//    @ManyToMany(fetch = FetchType.EAGER)
////    @JoinColumn(name = "summary_id", nullable = false)
//    @JoinTable(
//            name = "summary_tags",
//            joinColumns = @JoinColumn(name = "summaries_id"),
//            foreignKey = @ForeignKey(name = "summary_id"),
//            inverseJoinColumns = @JoinColumn(name = "summary_tags"),
//            inverseForeignKey = @ForeignKey(name = "tags_id")
//    )
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private Set<SummaryTags> summaryTags;