package ir.sharif.sad.controller;

import ir.sharif.sad.entity.User;
import ir.sharif.sad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RepositoryRestController
@RequestMapping("/login/")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/login")
    public ResponseEntity home() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        return ResponseEntity.ok("welcome");
    }
}
