package Com.VSummary.controller;

import Com.VSummary.domain.SimpleMessage;
import Com.VSummary.domain.entities.MySQL.Summaries;
import Com.VSummary.service.SummariesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Set;

@Controller
//@RequestMapping("createSummary")
public class SummariesController {

    @Autowired
    private SummariesService summariesService;

    @PostMapping("/createSummary")
    public String createSummary(@RequestParam long userId, Model model){
        return summariesService.createSummary(userId, model);
    }

    @PostMapping("/updateSummary")
    public String updateSummary(@RequestParam long summaryId, @RequestParam long userId, Model model) {
        return summariesService.updateSummary(summaryId, userId, model);
    }

    @PostMapping("/deleteSummary")
    @ResponseBody
    public ResponseEntity<String> deleteSummary(@RequestParam long summaryId, Model model) {
        return summariesService.deleteSummary(summaryId);
    }

    @PostMapping("/createSummary/addSummary")
    @ResponseBody
    public ResponseEntity<String> addSummary(
            @RequestBody Summaries summaries,
            @RequestParam(name = "userId") long userId,
            @RequestParam(name = "summaryTags") Set<String> summaryTags,
            Model model) {
        return summariesService.saveSummary(summaries, userId, summaryTags);
    }

    @MessageMapping("/search/update")
    public void searchName(Principal principal, SimpleMessage message) throws Exception {
        summariesService.sendSearchName(message.getMessage(), principal.getName());
    }

    @MessageMapping("/tags/update")
    public void searchTags(Principal principal, SimpleMessage message) throws Exception {
        summariesService.sendTags(message.getMessage(), principal.getName());
    }

    @PostMapping(
            value = "/main/uploadImg",
            consumes = "multipart/form-data")
    @ResponseBody
    public ResponseEntity<String> loadFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("idFiles") String[] idFiles,
            @RequestParam("username") String username) {
        return summariesService.loadFilesToServer(files, idFiles, username);
    }

    @MessageMapping("/createSummary/deleteImg")
    public void deleteFile(SimpleMessage message) {
        summariesService.deleteFileOfServer(message.getMessage());
    }
}