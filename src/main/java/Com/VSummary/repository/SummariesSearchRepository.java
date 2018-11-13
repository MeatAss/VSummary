package Com.VSummary.repository;

import Com.VSummary.domain.SummariesSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface SummariesSearchRepository extends ElasticsearchRepository<SummariesSearch, String> {
}