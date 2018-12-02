package Com.VSummary.controller;

import Com.VSummary.domain.entities.MySQL.User;
import Com.VSummary.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @Autowired
    private MainService mainService;

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User user, Model model) {
        return mainService.main(user, model);
    }

    @PostMapping("main/addComment")
    @ResponseBody
    public HttpStatus addComment(
            @AuthenticationPrincipal User user,
            @RequestParam("summaryId") long summaryId,
            @RequestParam("comment") String comment
    ) throws Exception {
        return mainService.addComment(user, summaryId, comment);
    }

    @PostMapping("main/addLike")
    @ResponseBody
    public ResponseEntity<String> addLike(
            @AuthenticationPrincipal User user,
            @RequestParam("commentId") long commentId
    ) throws Exception {
        return mainService.addLike(user, commentId);
    }

    @PostMapping("main/addRating")
    @ResponseBody
    public HttpStatus addRating(
            @AuthenticationPrincipal User user,
            @RequestParam("summaryId") long summaryId,
            @RequestParam("rating") byte ratingNumber
    ) throws Exception {
        return mainService.addRating(user, summaryId, ratingNumber);
    }
}
