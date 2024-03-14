package com.accountmanagement.controller.rest;

import com.accountmanagement.controller.main.Main;
import com.accountmanagement.model.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/rest/profile")
public class ProfileRestCont extends Main {
    @GetMapping
    public String profile(Model model) {
        getCurrentUserAndRole(model);
        return "profile";
    }

    @PostMapping("/edit")
    public ResponseEntity<Users> profileEdit(@RequestParam String fio, @RequestParam String password) {
        Users user = getUser();
        if (!password.isEmpty()) user.setPassword(password);
        user.setFio(fio);
        return ResponseEntity.ok(usersRepo.save(user));
    }
}
