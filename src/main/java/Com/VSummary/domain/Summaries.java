package Com.VSummary.domain;

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

//    public Summaries() {
//    }
//
//    public Summaries(String nameSummary, String shortDescription, String specialtyNumber, String sumaryTags, String textSummary) {
//        this.nameSummary = nameSummary;
//        this.shortDescription = shortDescription;
//        this.specialtyNumber = specialtyNumber;
//        this.sumaryTags = sumaryTags;
//        this.textSummary = textSummary;
//    }

//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getShortDescription() {
//        return shortDescription;
//    }
//
//    public void setShortDescription(String shortDescription) {
//        this.shortDescription = shortDescription;
//    }
//
//    public String getSpecialtyNumber() {
//        return specialtyNumber;
//    }
//
//    public void setSpecialtyNumber(String specialtyNumber) {
//        this.specialtyNumber = specialtyNumber;
//    }
//
//    public String getNameSummary() {
//        return nameSummary;
//    }
//
//    public void setNameSummary(String nameSummary) {
//        this.nameSummary = nameSummary;
//    }
//
//    public String getSumaryTags() {
//        return sumaryTags;
//    }
//
//    public void setSumaryTags(String sumaryTags) {
//        this.sumaryTags = sumaryTags;
//    }
//
//    public String getTextSummary() {
//        return textSummary;
//    }
//
//    public void setTextSummary(String textSummary) {
//        this.textSummary = textSummary;
//    }
}
