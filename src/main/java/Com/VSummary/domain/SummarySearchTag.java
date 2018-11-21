package Com.VSummary.domain;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.Document;

import org.springframework.data.elasticsearch.core.completion.Completion;
import javax.persistence.Id;

@Document(indexName = "tags-index-completion", type = "completion-type", shards = 1, replicas = 0, refreshInterval = "-1")
@Data
public class SummarySearchTag {

    @Id
    private String id;

    private String name;

    @Getter(AccessLevel.NONE)
    @CompletionField(maxInputLength = 100)
    private Completion suggest;

    public SummarySearchTag() {
    }

    public SummarySearchTag(String id) {
        this.id = id;
    }

    public Completion getSuggest() {
        return suggest;
    }
}
