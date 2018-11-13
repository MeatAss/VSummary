package Com.VSummary.domain;

import org.springframework.data.elasticsearch.core.completion.Completion;
import org.springframework.data.elasticsearch.core.query.IndexQuery;

import java.util.UUID;

public class SummarySearchTagBuilder {
    private SummarySearchTag result;

    public SummarySearchTagBuilder() {
        result = new SummarySearchTag(UUID.randomUUID().toString());
    }

    public SummarySearchTagBuilder(String id) {
        result = new SummarySearchTag(id);
    }

    public SummarySearchTagBuilder name(String name) {
        result.setName(name);
        return this;
    }

    public SummarySearchTagBuilder suggest(String[] input) {
        return suggest(input, null);
    }

    public SummarySearchTagBuilder suggest(String[] input, Integer weight) {
        Completion suggest = new Completion(input);
        suggest.setWeight(weight);

        result.setSuggest(suggest);
        return this;
    }

    public SummarySearchTag build() {
        return result;
    }

    public IndexQuery buildIndex() {
        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setId(result.getId());
        indexQuery.setObject(result);
        return indexQuery;
    }
}
