package Com.VSummary.controller;

import Com.VSummary.domain.SimpleMessage;
import Com.VSummary.domain.entities.MySQL.User;
import Com.VSummary.service.PersonalAreaService;
import com.sun.xml.internal.ws.dump.MessageDumping;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.security.Principal;

@Controller
@RequestMapping("/personalArea")
public class PersonalAreaController {

    @Autowired
    private PersonalAreaService personalAreaService;

    @GetMapping
    public String personalArea(@AuthenticationPrincipal User principal, @RequestParam("userId") long userId, Model model){
        return personalAreaService.personalArea(principal, userId, model);
    }

    @PostMapping("/update")
    @ResponseBody
    public HttpStatus simpleEmail(@RequestParam("name") String type, @RequestParam("value") String value, @RequestParam("userId") long userId){
        return personalAreaService.changePersonalData(type, value, userId);
    }

    @MessageMapping("/personalArea/filter")
    public void filterSummary(Principal principal, String json) throws Exception {
        personalAreaService.filterSummary(principal, json);
    }
}
