package Com.VSummary.controller;

import Com.VSummary.domain.entities.User;
import Com.VSummary.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/registration")
    public String registration(Map<String, Object> model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model){
        if (!authenticationService.addUser(user)) {
            model.put("message", "User exist");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean activateUser = authenticationService.activateUser(code);
        //return in model Success/no success
        return "login";
    }
}
