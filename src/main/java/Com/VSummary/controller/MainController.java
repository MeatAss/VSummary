package Com.VSummary.controller;

import Com.VSummary.domain.SimpleMessage;
import Com.VSummary.domain.Summaries;
import Com.VSummary.service.MainService;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {


    @Value("${ftp.host}")
    private String ftpHost;

    @Value("${ftp.port}")
    private int ftpPort;

    @Value("${ftp.login}")
    private String ftpLogin;

    @Value("${ftp.password}")
    private String ftpPassword;

    @Value("${ftp.workingdirectory}")
    private String ftpPath;



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

    @PostMapping(
            value = "/main/uploadImg",
            consumes = "multipart/form-data")
    @ResponseBody
    public ResponseEntity<Object> loadImg(@RequestParam("file") MultipartFile[] files) {
        if (files[0].isEmpty())
            return new ResponseEntity<Object>("{\"error\" : \"file empty\"}", HttpStatus.BAD_REQUEST);

        if (!files[0].getOriginalFilename().matches("([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)"))
            return new ResponseEntity<Object>("{\"error\" : \"not supporting format\"}", HttpStatus.BAD_REQUEST);

        String uploadFileName = UUID.randomUUID().toString() + files[0].getOriginalFilename();

        FTPClient con = null;

        try {
            con = new FTPClient();
            con.connect(ftpHost, ftpPort);

            if (con.login(ftpLogin, ftpPassword)) {
                con.enterLocalPassiveMode(); // important!
                con.setFileType(FTP.BINARY_FILE_TYPE);
                con.changeWorkingDirectory(ftpPath);

                boolean result = con.storeFile(uploadFileName, files[0].getInputStream());
                con.logout();
                con.disconnect();

                return new ResponseEntity<Object>("{\"success\" : \"success\"}", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<Object>("{\"error\" : \"Could not upload file\"}", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Object>("{\"error\" : \"error\"}", HttpStatus.BAD_REQUEST);
    }
}