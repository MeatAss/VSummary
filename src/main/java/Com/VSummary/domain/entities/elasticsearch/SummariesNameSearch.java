package Com.VSummary.domain.entities.elasticsearch;

import Com.VSummary.domain.entities.MySQL.Summaries;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;

@Document(indexName = "summaries_name_search", type = "summaries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SummariesNameSearch {
    @Id
    private String id;

    private String name;

    public SummariesNameSearch(Summaries summaries){
        id = String.valueOf(summaries.getId());
        name = summaries.getNameSummary();
    }
}