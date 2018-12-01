package Com.VSummary.service;

import Com.VSummary.domain.*;
import Com.VSummary.domain.entities.MySQL.Summaries;
import Com.VSummary.domain.entities.MySQL.SummaryTags;
import Com.VSummary.domain.entities.MySQL.User;
import Com.VSummary.domain.entities.elasticsearch.SummariesNameSearch;
import Com.VSummary.domain.entities.elasticsearch.SummariesSearch;
import Com.VSummary.domain.entities.elasticsearch.SummarySearchTag;
import Com.VSummary.repository.*;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Service
@Configuration
@ComponentScan
@EnableAsync
public class SummariesService {

    @Autowired
    private SummariesRepository summariesRepository;

    @Autowired
    private SummariesSearchRepository summariesSearchRepository;

    @Autowired
    private SummariesNameSearchRepository summariesNameSearchRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private AsuncDownloaderFTP asuncDownloaderFTP;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SummaryTagsRepository summaryTagsRepository;

    public String updateSummary(long summaryId, long userId, Model model) {
        Optional<Summaries> summary = summariesRepository.findById(summaryId);
        if (!summary.isPresent())
            return "redirect: /main";

        if (!userRepository.findById(userId).isPresent())
            return "redirect: /login";

        configureUpdateSummaryModel(model, summary.get(), userId);

        return "updateSummary";
    }

    private void configureUpdateSummaryModel(Model model, Summaries summaries, long userId) {
        model.addAttribute("originalUserId", userId);
        model.addAttribute("summaryId", summaries.getId());
        model.addAttribute("nameSummary", summaries.getNameSummary());
        model.addAttribute("shortDescription", summaries.getShortDescription());
        model.addAttribute("specialtyNumber", summaries.getSpecialtyNumber());
        model.addAttribute("summaryTags", summaries.getStringSummaryTags());
        model.addAttribute("textSummary", summaries.getTextSummary());
    }

    public ResponseEntity<String> deleteSummary(long summaryId) {
        Optional<Summaries> summary = summariesRepository.findById(summaryId);
        if (!summary.isPresent())
            return new ResponseEntity<>("summary not found", HttpStatus.BAD_REQUEST);

        summariesRepository.delete(summary.get());

        return new ResponseEntity<>(String.valueOf(summaryId), HttpStatus.OK);
    }

    public ResponseEntity<String> saveSummary(Summaries summary, long userId, Set<String> setTags) {
        if (setTags.size() == 0)
            return new ResponseEntity<>("tags is empty!", HttpStatus.BAD_REQUEST);

        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent())
            return new ResponseEntity<>("user not found", HttpStatus.BAD_REQUEST);

        saveSummaryToDB(summary, user.get(), setTags);

        return new ResponseEntity<>("http://localhost:8080/personalArea?userId=" + userId, HttpStatus.OK);
    }

    private void saveSummaryToDB(Summaries summary, User user, Set<String> setTags){
        summary.setSummaryTags(saveTags(setTags));
        summary.setUser(user);

        summariesRepository.save(summary);
        summariesSearchRepository.save(new SummariesSearch(summary));
        summariesNameSearchRepository.save(new SummariesNameSearch(summary));
    }

    private List<SummaryTags> saveTags(Set<String> tags) {
        Set<String> ignoreCaseTags = new TreeSet<>(String::compareToIgnoreCase);
        ignoreCaseTags.addAll(tags);

        List<String> mainList = new ArrayList<>(ignoreCaseTags);
        mainList.replaceAll(String::toLowerCase);

        saveNotEqualsTags(mainList);

        return summaryTagsRepository.findAllByTagIn(mainList);
    }

    private void saveNotEqualsTags(List<String> tags) {
        List<String> clone = new ArrayList<>(tags);

        List<String> inTags = summaryTagsRepository.findAllByTagIn(clone).stream().map(SummaryTags::getTag).collect(Collectors.toList());
        clone.removeAll(inTags);

        if (clone.size() > 0) {
            summaryTagsRepository.saveAll(clone.stream().map(SummaryTags::new).collect(Collectors.toList()));
            saveElasticsearchTags(clone);
        }
    }

    private void saveElasticsearchTags(List<String> tags) {
        List<IndexQuery> indexQueries = new ArrayList<>();

        tags.forEach(tag -> indexQueries.add(new SummarySearchTagBuilder().name(tag).suggest(new String[]{tag}).buildIndex()));

        elasticsearchTemplate.bulkIndex(indexQueries);
        elasticsearchTemplate.refresh(SummarySearchTag.class);
    }

    public void sendTags(String tag, String username) {
        SuggestionBuilder completionSuggestionFuzzyBuilder = SuggestBuilders
                .completionSuggestion("suggest")
                .prefix(tag, Fuzziness.AUTO);

        final SearchResponse suggestResponse  = elasticsearchTemplate.suggest(
                new SuggestBuilder().addSuggestion(
                        "test-suggest",
                        completionSuggestionFuzzyBuilder),
                SummarySearchTag.class);

        if (suggestResponse.getSuggest() == null)
            return;

        CompletionSuggestion completionSuggestion = suggestResponse.getSuggest().getSuggestion("test-suggest");
        List<CompletionSuggestion.Entry.Option> options = completionSuggestion.getEntries().get(0).getOptions();

        List<SimpleMessage> messages = new ArrayList<>();

        for (int i = 0; (i < options.size()) && (i < 5); i++) {
            messages.add(new SimpleMessage(options.get(i).getText().string()));
        }

        simpMessagingTemplate.convertAndSendToUser(username, "queue/updateTags", messages);
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

        List<SimpleMessage> simpleMessages = new ArrayList<>();
        results.forEach(result -> simpleMessages.add(new SimpleMessage(result.getName())));

        simpMessagingTemplate.convertAndSendToUser(username, "queue/searchData", simpleMessages);
    }

    public void deleteFileOfServer(String filePath) {
        asuncDownloaderFTP.asuncDeleteFile(filePath);
    }

    public ResponseEntity<String> loadFilesToServer(MultipartFile[] files, String[] idFiles, String username) {
        List<MultipartFileBuffer> multipartFileBuffers = new ArrayList<>();
        List<JSONImageData> jsonImageData = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {
            try {
                multipartFileBuffers.add(new MultipartFileBuffer(
                        idFiles[i],
                        files[i].getOriginalFilename(),
                        files[i].getInputStream())
                );

                jsonImageData.add(new JSONImageData(
                        idFiles[i],
                        AsuncDownloaderFTP.fullPath + multipartFileBuffers.get(i).getName(),
                        multipartFileBuffers.get(i).getName()
                ));
            } catch (IOException e) {
                //ERROR GETING FILE
            }
        }

        asuncDownloaderFTP.asuncLoadFiles(multipartFileBuffers, username);
        return new ResponseEntity<>(new JSONObject().put("files", jsonImageData).toString(), HttpStatus.OK);
    }

    public String createSummary(long userId, Model model){
        if (!userRepository.findById(userId).isPresent())
            return "redirect: /login";

        model.addAttribute("userId", userId);
        return "createSummary";
    }
}