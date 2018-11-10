package Com.VSummary.controller;

import Com.VSummary.domain.SimpleMessage;
import Com.VSummary.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private MainService mainService;

    @GetMapping("/main")
    public String main(Map<String, Object> model){
        return "createSummary";
    }

    @PostMapping("/main/addSummary")
    @ResponseBody
    public String post(@RequestBody List<String> summariesData, Map<String, Object> model) {
        return mainService.writeSummary(summariesData);
    }

    @MessageMapping("/search/update")
    public void searchData(Principal principal, SimpleMessage message) throws Exception {
        mainService.sendSearchName(message.getMessage(), principal.getName());
    }
}