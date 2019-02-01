package ir.sharif.sad.controller;


import ir.sharif.sad.dto.CharityDto;
import ir.sharif.sad.dto.FoundationDto;
import ir.sharif.sad.dto.ProjectDto;
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

@RepositoryRestController
@Secured("ROLE_FOUNDATION")
@RequestMapping("/foundation/")
public class FoundationController {
    private FoundationService foundationService;
    @Autowired
    public FoundationController(FoundationService foundationService){
        this.foundationService = foundationService;
    }

    @PostMapping("/sign_up")
    public ResponseEntity signUp(@RequestBody FoundationDto foundationDto) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(foundationService.save(foundationDto, auth.getName()));
    }

    @PostMapping("/create/{id}/project")
    public ResponseEntity createProject(@RequestBody ProjectDto projectDto, @PathVariable int id) throws Exception {
        return  ResponseEntity.ok(foundationService.createProject(projectDto, id));
    }

    @PostMapping("/create/{id}/charity")
    public ResponseEntity createCharity(@RequestBody CharityDto charityDto, @PathVariable int id) throws Exception{
        return ResponseEntity.ok(foundationService.createCharity(charityDto, id));
    }
        
}
