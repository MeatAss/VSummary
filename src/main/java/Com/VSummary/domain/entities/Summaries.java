package Com.VSummary.domain.entities;

//import org.springframework.data.elasticsearch.annotations.Document;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "summaries")
@Getter
@Setter
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
