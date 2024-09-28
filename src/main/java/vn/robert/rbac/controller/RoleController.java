package vn.robert.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.robert.rbac.service.RoleService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAll(@PathVariable Long userId) {
        return ResponseEntity.ok(roleService.getAll(3L));
    }

}
