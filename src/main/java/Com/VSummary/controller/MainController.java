package Com.VSummary.controller;

import Com.VSummary.domain.Summaries;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(Map<String, Object> model){
        return "createSummary";
    }
}
