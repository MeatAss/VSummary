package Com.VSummary.controller;

import Com.VSummary.domain.entities.MySQL.SummaryTags;
import Com.VSummary.repository.SummariesRepository;
import Com.VSummary.repository.SummaryTagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private SummariesRepository summariesRepository;

    @Autowired
    private SummaryTagsRepository summaryTagsRepository;

    @GetMapping("/main")
    public String main(Model model) {
//        String[] tags = new String[] {"asd", "asd", "asd2", "asd2", "asd1"};
//
//        List<SummaryTags> st = summaryTagsRepository.findAllByTagNotIn(tags);


        model.addAttribute("summaries", summariesRepository.findAll());

        return "main";
    }
}
