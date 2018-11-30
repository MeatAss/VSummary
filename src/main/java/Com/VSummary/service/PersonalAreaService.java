package Com.VSummary.service;

import Com.VSummary.domain.entities.MySQL.User;
import Com.VSummary.domain.enums.Role;
import Com.VSummary.repository.SummariesRepository;
import Com.VSummary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

@Service
public class PersonalAreaService {

    @Autowired
    private SummariesRepository summariesRepository;

    @Autowired
    private UserRepository userRepository;


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
}
