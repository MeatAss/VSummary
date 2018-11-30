package Com.VSummary.controller;

import Com.VSummary.domain.entities.MySQL.User;
import Com.VSummary.service.AdministratorAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/administratorArea")
@PreAuthorize("hasAuthority('ADMINISTRATOR')")
public class AdministratorAreaController {

    @Autowired
    private AdministratorAreaService administratorAreaService;

    @GetMapping
    public String adminMenu(@AuthenticationPrincipal User user, Model model) {
        return administratorAreaService.adminMenu(user, model);
    }

    @PostMapping("/changeRole")
    @ResponseBody
    public HttpStatus changeRole(@RequestParam("pk") long userId, @RequestParam("value") String role){
        return administratorAreaService.changeRole(userId, role);
    }

    @PostMapping("/changeStatus")
    @ResponseBody
    public HttpStatus changeStatus(@RequestParam("pk") long userId, @RequestParam("value") boolean enable){
        return administratorAreaService.changeStatus(userId, enable);
    }

    @PostMapping("/showUser")
    @ResponseBody
    public ResponseEntity<String> showUser(@RequestParam("userId") long userId){
        return administratorAreaService.showUser(userId);
    }
}
