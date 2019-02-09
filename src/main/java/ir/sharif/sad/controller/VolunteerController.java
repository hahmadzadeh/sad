package ir.sharif.sad.controller;

import ir.sharif.sad.dto.PaymentDto;
import ir.sharif.sad.dto.VolunteerDto;
import ir.sharif.sad.dto.VolunteerRequestDto;
import ir.sharif.sad.entity.Volunteer;
import ir.sharif.sad.exceptions.EntityNotExistException;
import ir.sharif.sad.exceptions.VolunteerExistException;
import ir.sharif.sad.service.VolunteerService;
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
@Secured("ROLE_VOLUNTEER")
@RequestMapping("/volunteer/")
public class VolunteerController {
    private VolunteerService volunteerService;

    @Autowired
    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody VolunteerDto volunteerDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            Volunteer volunteer = volunteerService.
                    fillAbilities(volunteerDto, volunteerService.save(volunteerDto, auth.getName()));
            return ResponseEntity.ok(VolunteerDto.volunteer2VolunteerDto(volunteer));
        } catch (VolunteerExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity updateProfile(@RequestBody VolunteerDto volunteerDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            Volunteer volunteer = volunteerService.updateProfile(volunteerDto, auth.getName());
            volunteer = volunteerService.fillAbilities(volunteerDto, volunteer);
            return ResponseEntity.ok(VolunteerDto.volunteer2VolunteerDto(volunteer));
        } catch (EntityNotExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/read/projects")
    public ResponseEntity readProjects(Pageable page, @RequestParam(required = false) String filter) {
        Filter filterObj = new Filter(filter);
        return ResponseEntity.ok(volunteerService.readProjects(page, filterObj));
    }

    @GetMapping("/read/project/{id}")
    public ResponseEntity readProject(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(volunteerService.readOneProject(id));
        } catch (EntityNotExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/read/my-profile")
    public ResponseEntity readMyProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            return ResponseEntity.ok(volunteerService.readOne(auth.getName()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/read/my-payments")
    public ResponseEntity readMyPayments(Pageable page, @RequestParam(required = false) String filter) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Filter filterObj = new Filter(filter);
        return ResponseEntity.ok(volunteerService.readMyPayments(page, filterObj, auth.getName()));
    }

    @GetMapping("/read/charities/{page}")
    public ResponseEntity readCharities(@PathVariable Integer page) {
        return ResponseEntity.ok(volunteerService.readCharities(page));
    }

    @PostMapping("/create/payment")
    public ResponseEntity makePayment(@RequestBody PaymentDto paymentDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            return ResponseEntity.ok(volunteerService
                    .makePayment(paymentDto.getAmount(), auth.getName(), paymentDto.getProjectId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/create/{id}/request")
    public ResponseEntity makeRequest(@RequestBody VolunteerRequestDto dto, @PathVariable Integer id) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(volunteerService.makeRequest(dto, auth.getName(), id));
    }
}
