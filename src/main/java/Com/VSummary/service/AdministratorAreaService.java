package Com.VSummary.service;

import Com.VSummary.domain.entities.MySQL.User;
import Com.VSummary.domain.enums.Role;
import Com.VSummary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

@Service
public class AdministratorAreaService {

    @Autowired
    private UserRepository userRepository;

    public String adminMenu(User user, Model model) {
        model.addAttribute("users", userRepository.findAllByIdNotIn(user.getId()));

        return "administratorArea";
    }

    public HttpStatus changeRole(long userId, String role) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (!optionalUser.isPresent() || !Role.isExist(role))
            return HttpStatus.BAD_REQUEST;

        userRepository.save(changeRoles(optionalUser.get(), role));

        return HttpStatus.OK;
    }

    private User changeRoles(User user, String role){
        user.getRoles().clear();
        user.getRoles().add(Role.USER);

        if (Role.ADMINISTRATOR.name().equalsIgnoreCase(role))
            user.getRoles().add(Role.ADMINISTRATOR);

        return user;
    }

    public HttpStatus changeStatus(long userId, boolean enable) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (!optionalUser.isPresent())
            return HttpStatus.BAD_REQUEST;

        optionalUser.get().setEnable(enable);
        userRepository.save(optionalUser.get());

        return HttpStatus.OK;
    }

    public ResponseEntity<String> showUser(long userId) {
        if (!userRepository.findById(userId).isPresent())
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>("personalArea?userId=" + userId, HttpStatus.OK);
    }
}
