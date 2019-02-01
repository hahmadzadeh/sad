package ir.sharif.sad.controller;

import ir.sharif.sad.repository.UserRepository;
import ir.sharif.sad.service.FoundationService;
import ir.sharif.sad.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RepositoryRestController
@RequestMapping("/login/")
public class LoginController {

    private final FoundationService foundationService;
    private final VolunteerService volunteerService;
    private final UserRepository userRepository;

    @Autowired
    public LoginController(FoundationService foundationService, VolunteerService volunteerService, UserRepository userRepository) {
        this.foundationService = foundationService;
        this.volunteerService = volunteerService;
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/foundation")
    public ResponseEntity homeFoundation() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(foundationService.readOne(auth.getName()));
    }

    @PostMapping(value = "/volunteer")
    public ResponseEntity homeVolunteer() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(volunteerService.readOne(auth.getName()));
    }

    @PostMapping
    public ResponseEntity homeAdmin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok("welcome " + auth.getName());
    }
}
