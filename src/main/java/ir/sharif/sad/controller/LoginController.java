package ir.sharif.sad.controller;

import ir.sharif.sad.entity.User;
import ir.sharif.sad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity createNewUser(@RequestBody User user) {
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            return ResponseEntity.ok("email is taken");
        }
        userService.saveUser(user, "ADMIN");
        return ResponseEntity.ok("OK");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity home() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        return ResponseEntity.ok("welcome");
    }


}
