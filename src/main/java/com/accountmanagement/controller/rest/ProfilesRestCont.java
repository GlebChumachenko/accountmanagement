package com.accountmanagement.controller.rest;

import com.accountmanagement.controller.main.Main;
import com.accountmanagement.model.Users;
import com.accountmanagement.model.enums.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rest/profiles")
public class ProfilesRestCont extends Main {

    @GetMapping
    public ResponseEntity<List<Users>> profiles() {
        return ResponseEntity.ok(usersRepo.findAll());
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Users> profileRoleEdit(@PathVariable Long id, @RequestParam Role role) {
        Users user = usersRepo.getReferenceById(id);
        user.setRole(role);
        return ResponseEntity.ok(usersRepo.save(user));
    }
}
