package Com.VSummary.repository;

import Com.VSummary.domain.SummariesSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SummariesSearchRepository extends ElasticsearchRepository<SummariesSearch, String> {
}