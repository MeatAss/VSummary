package Com.VSummary.service;

import Com.VSummary.domain.entities.MySQL.Summaries;
import Com.VSummary.domain.entities.MySQL.User;
import Com.VSummary.domain.entities.elasticsearch.SummariesNameSearch;
import Com.VSummary.domain.enums.Role;
import Com.VSummary.repository.SummariesNameSearchRepository;
import Com.VSummary.repository.SummariesRepository;
import Com.VSummary.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@Service
public class PersonalAreaService {

    @Autowired
    private SummariesRepository summariesRepository;

    @Autowired
    private SummariesNameSearchRepository summariesNameSearchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public String personalArea(User principal, Long userId, Model model) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (!checkUser(principal, optionalUser))
            return "redirect:login";

        setPersonalData(optionalUser.get(), model);

        return "personalArea";
    }

    private boolean checkUser(User principal, Optional<User> optionalUser){
        return ((optionalUser.isPresent()) &&
                (principal.getRoles().contains(Role.ADMINISTRATOR) ||
                    (optionalUser.get().equals(principal))));
    }

    private void setPersonalData(User user, Model model) {
        model.addAttribute("givenName", user.getGivenName());
        model.addAttribute("secondName", user.getSecondName());

        model.addAttribute("originalUserId", user.getId());
        model.addAttribute("summaries", summariesRepository.findAllByUser(user));
    }

    public HttpStatus changePersonalData(String fieldName, String value, long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (changeFieldValue(user, fieldName, value)){
                userRepository.save(user);
                return HttpStatus.OK;
            }
        }

        return HttpStatus.BAD_REQUEST;
    }

    private boolean changeFieldValue(User user, String fieldName, String value) {
        if (value.length() <= 0 || User.nameMaxLength <= value.length())
            return false;

        if (fieldName.equals("givenName")){
            user.setGivenName(value);
            return true;
        }

        if (fieldName.equals("secondName")){
            user.setSecondName(value);
            return true;
        }

        return false;
    }

    public void filterSummary(Principal principal, String json) {
        JSONObject jsonObj=new JSONObject(json);

        Iterable<SummariesNameSearch> search =
                summariesNameSearchRepository.search(queryStringQuery("*" + jsonObj.getString("filter") + "*"));
        List<Summaries> summaries = new ArrayList<>();
        search.forEach(item -> {
            summaries.add(summariesRepository.findById(Long.parseLong(item.getId())).get());
        });

        sortSummaries(summaries, jsonObj.getInt("sorting"));

        simpMessagingTemplate.convertAndSendToUser(
                principal.getName(),
                "queue/personalArea/filter",
                new JSONObject().put("summaries", summaries).toString());
    }

    private void sortSummaries(List<Summaries> summaries, int sorting){
        if (sorting == 0)
            return;

        summaries.sort(Comparator.comparing(Summaries::getNameSummary));

        if (sorting < 0)
            Collections.reverse(summaries);
    }
}