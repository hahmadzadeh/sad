package ir.sharif.sad.controller;

import ir.sharif.sad.repository.UserRepository;
import ir.sharif.sad.service.FoundationService;
import ir.sharif.sad.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;

@RepositoryRestController
@RequestMapping("/login/")
public class LoginController {
    @PostMapping(value = "/foundation")
    @Secured("ROLE_FOUNDATION")
    public ResponseEntity homeFoundation(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok("welcome " + auth.getName());
    }

    @PostMapping(value = "/volunteer")
    @Secured("ROLE_VOLUNTEER")
    public ResponseEntity homeVolunteer(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok("welcome " + auth.getName());
    }

    @PostMapping(value = "/admin")
    @Secured("ROLE_ADMIN")
    public ResponseEntity homeAdmin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok("welcome " + auth.getName());
    }
}
