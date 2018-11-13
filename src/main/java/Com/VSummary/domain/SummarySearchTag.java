package Com.VSummary.domain;

import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.Document;

import org.springframework.data.elasticsearch.core.completion.Completion;
import javax.persistence.Id;

@Document(indexName = "tags-index-completion", type = "completion-type", shards = 1, replicas = 0, refreshInterval = "-1")
public class SummarySearchTag {
    @Id
    private String id;
    private String name;

    @CompletionField(maxInputLength = 100)
    private Completion suggest;

    public SummarySearchTag() {
    }

    public SummarySearchTag(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Completion getSuggest() {
        return suggest;
    }

    public void setSuggest(Completion suggest) {
        this.suggest = suggest;
    }
}
