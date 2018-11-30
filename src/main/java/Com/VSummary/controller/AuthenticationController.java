package Com.VSummary.controller;

import Com.VSummary.domain.SimpleMessage;
import Com.VSummary.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/openAuthentication")
    @ResponseBody
    public ResponseEntity<String> openAuthentication(@RequestBody SimpleMessage message, Model model) {
        return authenticationService.openAuthentication(message.getMessage());
    }

    @PostMapping("/registration/{type}")
    @ResponseBody
    public ResponseEntity<String> simpleEmail(
            @PathVariable("type") String type,
            @RequestParam("email") String email,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model){

        return authenticationService.sendSimpleUserActivationCode(type, email, username, password);
    }

    @GetMapping("/activate/Simple/{type}")
    @ResponseBody
    public ResponseEntity<String> activateSimple(
            Model model,
            @PathVariable("type") String type,
            @RequestParam("code") String code) {

        return authenticationService.activeSimple(type, code);
    }

    @GetMapping("/activate/OAuth1/{type}")
    @ResponseBody
    public ResponseEntity<String> activateOAuth1(
            Model model,
            @PathVariable("type") String type,
            @RequestParam("oauth_token") String oauthToken,
            @RequestParam("oauth_verifier") String oauthVerifier) {

        return authenticationService.activateOAuth1User(type, oauthToken, oauthVerifier);
    }

    @GetMapping("/activate/OAuth2/{type}")
    @ResponseBody
    public ResponseEntity<String> activateOAuth2(
            Model model,
            @PathVariable("type") String type,
            @RequestParam("code") String code) {

        return authenticationService.activateOAuth2User(type, code);
    }
}
