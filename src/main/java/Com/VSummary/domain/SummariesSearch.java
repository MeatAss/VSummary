package Com.VSummary.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;

@Document(indexName = "summaries_search", type = "summaries")
public class SummariesSearch {

    @Id
    private String id;
    private String idSummary;
    private String name;
    private String shortDescription;
    private String specialtyNumber;
    private String text;

    public SummariesSearch() {
    }

    public SummariesSearch(String idSummary, String name, String shortDescription, String specialtyNumber, String text) {
        this.idSummary = idSummary;
        this.name = name;
        this.shortDescription = shortDescription;
        this.specialtyNumber = specialtyNumber;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdSummary() {
        return idSummary;
    }

    public void setIdSummary(String idSummary) {
        this.idSummary = idSummary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
