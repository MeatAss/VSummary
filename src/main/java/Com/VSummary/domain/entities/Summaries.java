package Com.VSummary.domain.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "summaries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Summaries implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String nameSummary;
    private String shortDescription;
    private String specialtyNumber;
    private String sumaryTags;//change summaryTags!!!!!!!!!!

    @Lob
    private String textSummary;
}
