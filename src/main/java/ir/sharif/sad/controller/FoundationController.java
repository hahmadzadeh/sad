package ir.sharif.sad.controller;


import ir.sharif.sad.dto.FoundationUserDto;
import ir.sharif.sad.dto.ProjectDto;
import ir.sharif.sad.entity.Foundation;
import ir.sharif.sad.repository.FoundationRepository;
import ir.sharif.sad.service.FoundationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

@RepositoryRestController
@Secured("ROLE_FOUNDATION")
@RequestMapping("/foundations/")
public class FoundationController {
    private FoundationService foundationService;
    @Autowired
    public FoundationController(FoundationService foundationService){
        this.foundationService = foundationService;
    }

    @PostMapping("/sign_up")
    public ResponseEntity signUp(@RequestBody FoundationUserDto foundationDto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(foundationService.save(foundationDto, auth.getName()));
    }

    @PostMapping("/create/{id}/project")
    public ResponseEntity createProject(@RequestBody ProjectDto projectDto, @PathVariable long id){


    }
        
}
