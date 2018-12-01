package Com.VSummary.repository;

import Com.VSummary.domain.entities.elasticsearch.SummariesNameSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SummariesNameSearchRepository extends ElasticsearchRepository<SummariesNameSearch, String> {
}
