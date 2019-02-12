package ir.sharif.sad.controller;

import ir.sharif.sad.dto.AdminUserDto;
import ir.sharif.sad.enumerators.Roles;
import ir.sharif.sad.repository.CharityRepository;
import ir.sharif.sad.repository.ProjectRepository;
import ir.sharif.sad.repository.UserRepository;
import ir.sharif.sad.service.AdminService;
import ir.sharif.sad.specification.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("/admin/")
@CrossOrigin(origins = "*")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/create/admin")
    public ResponseEntity createAdmin(@RequestBody AdminUserDto adminUserDto){
        return ResponseEntity.ok(adminService.createAdmin(adminUserDto, Roles.ADMIN));
    }
    @GetMapping("/read/users")
    public ResponseEntity readAllUser(Pageable page, @RequestParam(required = false) String filter){
        Filter filterObj = new Filter(filter);
        return ResponseEntity.ok(adminService.readAll(page, filterObj, UserRepository.class));
    }
    @GetMapping("/read/projects")
    public ResponseEntity readAllProject(Pageable page, @RequestParam(required = false) String filter){
        Filter filterObj = new Filter(filter);
        return ResponseEntity.ok(adminService.readAll(page, filterObj, ProjectRepository.class));
    }
    @GetMapping("/read/charities")
    public ResponseEntity readAllCharity(Pageable page, @RequestParam(required = false) String filter){
        Filter filterObj = new Filter(filter);
        return ResponseEntity.ok(adminService.readAll(page, filterObj, CharityRepository.class));
    }
}
