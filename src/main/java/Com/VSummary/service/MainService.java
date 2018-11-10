package Com.VSummary.service;

import Com.VSummary.domain.SimpleMessage;
import Com.VSummary.domain.Summaries;
import Com.VSummary.domain.SummariesSearch;
import Com.VSummary.repository.SummariesRepository;
import Com.VSummary.repository.SummariesSearchRepository;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Service
public class MainService {
    @Autowired
    private SummariesRepository summariesRepository;
    @Autowired
    private SummariesSearchRepository summariesSearchRepository;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public String writeSummary(List<String> summariesData) {
        summariesRepository.save(new Summaries(
                summariesData.get(0),
                summariesData.get(1),
                summariesData.get(2),
                summariesData.get(3),
                summariesData.get(4)
        ));
        summariesSearchRepository.save(new SummariesSearch(
                Long.toString(summariesRepository.findByNameSummary(summariesData.get(0)).getId()),
                summariesData.get(0),
                summariesData.get(1),
                summariesData.get(2),
                summariesData.get(4)
        ));

        return "{\"success\":true}";
    }

    public void sendSearchName(String search, String username) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(search)
                        .field("name")
                        .field("shortDescription")
                        .field("specialtyNumber")
                        .field("text")
                        .type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
                        .fuzziness(Fuzziness.AUTO)
                        .prefixLength(3)
                )
                .build();

        Page<SummariesSearch> results = summariesSearchRepository.search(searchQuery);

        List<SimpleMessage> simpleMessages = new ArrayList<SimpleMessage>();
        results.forEach(result -> simpleMessages.add(new SimpleMessage(result.getName())));

        simpMessagingTemplate.convertAndSendToUser(username, "queue/searchData", simpleMessages);
    }
}
