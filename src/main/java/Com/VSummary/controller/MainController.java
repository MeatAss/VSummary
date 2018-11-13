package Com.VSummary.controller;

import Com.VSummary.domain.SimpleMessage;
import Com.VSummary.domain.Summaries;
import Com.VSummary.domain.SummarySearchTag;
import Com.VSummary.domain.SummarySearchTagBuilder;
import Com.VSummary.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private MainService mainService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @GetMapping("/main")
    public String main(Map<String, Object> model){
        /////////////CREATE SUMMARIES TAGS////////////
//        elasticsearchTemplate.deleteIndex(SummarySearchTag.class);
//        elasticsearchTemplate.createIndex(SummarySearchTag.class);
//        elasticsearchTemplate.refresh(SummarySearchTag.class);
//        elasticsearchTemplate.putMapping(SummarySearchTag.class);
//
//        List<IndexQuery> indexQueries = new ArrayList<>();
//        indexQueries.add(new SummarySearchTagBuilder("1").name("конспект").suggest(new String[]{"конспект"}).buildIndex());
//        indexQueries.add(new SummarySearchTagBuilder("2").name("тетрадь").suggest(new String[]{"тетрадь"}).buildIndex());
//        indexQueries.add(new SummarySearchTagBuilder("3").name("контрольная").suggest(new String[]{"контрольная"}).buildIndex());
//        indexQueries.add(new SummarySearchTagBuilder("4").name("конверт").suggest(new String[]{"конверт"}).buildIndex());
//        indexQueries.add(new SummarySearchTagBuilder("5").name("коньюнкция").suggest(new String[]{"коньюнкция"}).buildIndex());
//        indexQueries.add(new SummarySearchTagBuilder("6").name("контакт").suggest(new String[]{"контакт"}).buildIndex());
//        indexQueries.add(new SummarySearchTagBuilder("7").name("конституция").suggest(new String[]{"конституция"}).buildIndex());
//        indexQueries.add(new SummarySearchTagBuilder("8").name("конон").suggest(new String[]{"конон"}).buildIndex());
//
//        elasticsearchTemplate.bulkIndex(indexQueries);
//        elasticsearchTemplate.refresh(SummarySearchTag.class);
        /////////////////////////////////////////////
        return "createSummary";
    }

    @PostMapping("/main/addSummary")
    @ResponseBody
    public String addSummary(@RequestBody Summaries summaries, Map<String, Object> model) {
        return mainService.writeSummary(summaries);
    }

    @MessageMapping("/search/update")
    public void searchName(Principal principal, SimpleMessage message) throws Exception {
        mainService.sendSearchName(message.getMessage(), principal.getName());
    }

    @MessageMapping("/tags/update")
    public void searchTags(Principal principal, SimpleMessage message) throws Exception {
        mainService.sendTags(message.getMessage(), principal.getName());
    }
}