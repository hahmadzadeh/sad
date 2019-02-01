package ir.sharif.sad.controller;

import ir.sharif.sad.dto.AdminUserDto;
import ir.sharif.sad.enumerators.Roles;
import ir.sharif.sad.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.ok(adminService.creatAdmin(adminUserDto, Roles.ADMIN));
    }
}
