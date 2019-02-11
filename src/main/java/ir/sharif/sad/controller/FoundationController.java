package ir.sharif.sad.controller;


import ir.sharif.sad.dto.CharityDto;
import ir.sharif.sad.dto.FoundationDto;
import ir.sharif.sad.dto.ProjectDto;
import ir.sharif.sad.exceptions.EntityNotExistException;
import ir.sharif.sad.service.FoundationService;
import ir.sharif.sad.specification.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RepositoryRestController
@Secured("ROLE_FOUNDATION")
@RequestMapping("/foundation/")
public class FoundationController {
    private FoundationService foundationService;

    @Autowired
    public FoundationController(FoundationService foundationService) {
        this.foundationService = foundationService;
    }

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody FoundationDto foundationDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            return ResponseEntity.ok(foundationService.save(foundationDto, auth.getName()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody FoundationDto foundationDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(foundationService.update(foundationDto, auth.getName()));
    }

    @PostMapping("/create/project")
    public ResponseEntity createProject(@RequestBody ProjectDto projectDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            return ResponseEntity.ok(foundationService.createProject(projectDto, auth.getName()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/read/projects")
    public ResponseEntity readMyProject(Pageable page, @RequestParam String filter) {
        Filter filterObj = new Filter(filter);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(foundationService.readProjects(filterObj, auth.getName(), page));
    }

    @GetMapping("/read/charities")
    public ResponseEntity readMyCharity(Pageable page, @RequestParam String filter) {
        Filter filterObj = new Filter(filter);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(foundationService.readCharity(filterObj, auth.getName(), page));
    }

    @GetMapping("/read/charity/{id}/requests")
    public ResponseEntity readRequests(Pageable page, @PathVariable Integer id) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return ResponseEntity.ok(foundationService.readMyRequests(page, id, auth.getName()));
        } catch (EntityNotExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/approve/request/{id}")
    public ResponseEntity approveRequest(@PathVariable Integer id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            return ResponseEntity.ok(foundationService.approveRequest(id, auth.getName()));
        } catch (EntityNotExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create/charity")
    public ResponseEntity createCharity(@RequestBody CharityDto charityDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            return ResponseEntity.ok(foundationService.createCharity(charityDto, auth.getName()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
