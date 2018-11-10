package Com.VSummary.domain;

//import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;

@Entity
@Table(name = "summaries")
public class Summaries {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String nameSummary;
    private String shortDescription;
    private String specialtyNumber;
    private String sumaryTags;

    @Lob
    private String textSummary;

    public Summaries() {
    }

    public Summaries(String nameSummary, String shortDescription, String specialtyNumber, String sumaryTags, String textSummary) {
        this.nameSummary = nameSummary;
        this.shortDescription = shortDescription;
        this.specialtyNumber = specialtyNumber;
        this.sumaryTags = sumaryTags;
        this.textSummary = textSummary;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getSpecialtyNumber() {
        return specialtyNumber;
    }

    public void setSpecialtyNumber(String specialtyNumber) {
        this.specialtyNumber = specialtyNumber;
    }

    public String getNameSummary() {
        return nameSummary;
    }

    public void setNameSummary(String nameSummary) {
        this.nameSummary = nameSummary;
    }

    public String getSumaryTags() {
        return sumaryTags;
    }

    public void setSumaryTags(String sumaryTags) {
        this.sumaryTags = sumaryTags;
    }

    public String getTextSummary() {
        return textSummary;
    }

    public void setTextSummary(String textSummary) {
        this.textSummary = textSummary;
    }
}
