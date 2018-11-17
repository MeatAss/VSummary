package Com.VSummary.controller;

import Com.VSummary.domain.SimpleMessage;
import Com.VSummary.domain.entities.Summaries;
import Com.VSummary.service.CreateSummariesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Map;

@Controller
public class CreateSummariesController {

    @Autowired
    private CreateSummariesService createSummariesService;

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
        return createSummariesService.writeSummary(summaries);
    }

    @MessageMapping("/search/update")
    public void searchName(Principal principal, SimpleMessage message) throws Exception {
        createSummariesService.sendSearchName(message.getMessage(), principal.getName());
    }

    @MessageMapping("/tags/update")
    public void searchTags(Principal principal, SimpleMessage message) throws Exception {
        createSummariesService.sendTags(message.getMessage(), principal.getName());
    }

    @PostMapping(
            value = "/main/uploadImg",
            consumes = "multipart/form-data")
    @ResponseBody
    public ResponseEntity<String> loadFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("idFiles") String[] idFiles,
            @RequestParam("username") String username) {
        return createSummariesService.loadFilesToServer(files, idFiles, username);
    }

    @MessageMapping("/main/deleteImg")
    public void deleteFile(SimpleMessage message) {
        createSummariesService.deleteFileOfServer(message.getMessage());
    }
}