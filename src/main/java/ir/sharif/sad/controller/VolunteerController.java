package ir.sharif.sad.controller;

import ir.sharif.sad.dto.FoundationDto;
import ir.sharif.sad.dto.VolunteerDto;
import ir.sharif.sad.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RepositoryRestController
@Secured("ROLE_VOLUNTEER")
@RequestMapping("/volunteers/")
public class VolunteerController {
    private VolunteerService volunteerService;
    @Autowired
    public VolunteerController (VolunteerService volunteerService){
        this.volunteerService = volunteerService;
    }

    @PostMapping("/sign_up")
    public ResponseEntity signUp(@RequestBody VolunteerDto volunteerDto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(volunteerService.save(volunteerDto, auth.getName()));
    }

}
