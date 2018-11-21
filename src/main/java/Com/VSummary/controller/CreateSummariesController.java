package Com.VSummary.controller;

import Com.VSummary.domain.SimpleMessage;
import Com.VSummary.domain.entities.Summaries;
import Com.VSummary.service.CreateSummariesService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/createSummary/addSummary")
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

    @MessageMapping("/createSummary/deleteImg")
    public void deleteFile(SimpleMessage message) {
        createSummariesService.deleteFileOfServer(message.getMessage());
    }
}