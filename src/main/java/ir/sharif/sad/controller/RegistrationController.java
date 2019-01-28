package ir.sharif.sad.controller;

import ir.sharif.sad.dto.FoundationUserDto;
import ir.sharif.sad.dto.VolunteerUserDto;
import ir.sharif.sad.entity.User;
import ir.sharif.sad.enumerators.Roles;
import ir.sharif.sad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RepositoryRestController
@RequestMapping("/register")
public class RegistrationController {
    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/foundation")
    public ResponseEntity registerFoundation(@RequestBody FoundationUserDto user){
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            return ResponseEntity.ok("email is taken");
        }
        return ResponseEntity.ok(userService.saveUser(user.foundationUserDto2User(), Roles.FOUNDATION));
    }
    @PostMapping("/volunteer")
    public ResponseEntity registerFoundation(@RequestBody VolunteerUserDto user){
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            return ResponseEntity.ok("email is taken");
        }
        return ResponseEntity.ok(userService.saveUser(user.volunteerUserDto2User(), Roles.VOLUNTEER));
    }
}
