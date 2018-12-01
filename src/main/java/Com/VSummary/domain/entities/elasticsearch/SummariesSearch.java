package Com.VSummary.domain.entities.elasticsearch;

import Com.VSummary.domain.entities.MySQL.Summaries;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "summaries_search", type = "summaries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SummariesSearch extends SummariesNameSearch {

    private String shortDescription;

    private String specialtyNumber;

    private String text;

    public SummariesSearch(Summaries summaries) {
        super(summaries);

        shortDescription = summaries.getShortDescription();
        specialtyNumber = summaries.getSpecialtyNumber();
        text = summaries.getTextSummary();
    }
}
