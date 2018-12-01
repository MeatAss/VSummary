package Com.VSummary.controller;

import Com.VSummary.domain.entities.MySQL.Summaries;
import Com.VSummary.domain.entities.MySQL.SummaryTags;
import Com.VSummary.domain.entities.elasticsearch.SummariesNameSearch;
import Com.VSummary.domain.entities.elasticsearch.SummariesSearch;
import Com.VSummary.repository.SummariesNameSearchRepository;
import Com.VSummary.repository.SummariesRepository;
import Com.VSummary.repository.SummariesSearchRepository;
import Com.VSummary.repository.SummaryTagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private SummariesRepository summariesRepository;

//    @Autowired
//    private SummariesSearchRepository summariesSearchRepository;
//
//    @Autowired
//    private SummariesNameSearchRepository summariesNameSearchRepository;

    @GetMapping("/main")
    public String main(Model model) {
//        List<Summaries> all = summariesRepository.findAll();
//        List<SummariesSearch> all2 = new ArrayList<>();
//        List<SummariesNameSearch> all3 = new ArrayList<>();
//
//        all.forEach(item -> {
//            all2.add(new SummariesSearch(item));
//        });
//        all2.forEach(item -> {
//            all3.add(new SummariesNameSearch(item.getId(), item.getName()));
//        });
//
//        summariesSearchRepository.saveAll(all2);
//        summariesNameSearchRepository.saveAll(all3);

        model.addAttribute("summaries", summariesRepository.findAll());

        return "main";
    }
}
