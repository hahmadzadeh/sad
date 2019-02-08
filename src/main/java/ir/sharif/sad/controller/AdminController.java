package ir.sharif.sad.controller;

import ir.sharif.sad.dto.AdminUserDto;
import ir.sharif.sad.enumerators.Roles;
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
    public ResponseEntity readAllUser(Pageable page, @RequestParam String filter){
        Filter filterObj = new Filter(filter);
        return ResponseEntity.ok(adminService.readAll(page, filterObj));
    }
}
