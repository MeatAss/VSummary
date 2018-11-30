package Com.VSummary.domain.entities.elasticsearch;

import Com.VSummary.domain.entities.MySQL.Summaries;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;
import java.util.UUID;

@Document(indexName = "summaries_search", type = "summaries")
@Data
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

    public SummariesSearch(Summaries summaries) {
        id = UUID.randomUUID().toString();
        idSummary = String.valueOf(summaries.getId());
        name = summaries.getNameSummary();
        shortDescription = summaries.getShortDescription();
        specialtyNumber = summaries.getSpecialtyNumber();
        text = summaries.getTextSummary();
    }
}
