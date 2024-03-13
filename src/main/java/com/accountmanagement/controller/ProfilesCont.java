package com.accountmanagement.controller;

import com.accountmanagement.controller.main.Main;
import com.accountmanagement.model.Users;
import com.accountmanagement.model.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profiles")
public class ProfilesCont extends Main {

    @GetMapping
    public String profiles(Model model) {
        getCurrentUserAndRole(model);
        model.addAttribute("roles", Role.values());
        model.addAttribute("users", usersRepo.findAll());
        return "profiles";
    }

    @PostMapping("/edit/{id}")
    public String profileRoleEdit(@PathVariable Long id, @RequestParam Role role) {
        Users user = usersRepo.getReferenceById(id);
        user.setRole(role);
        usersRepo.save(user);
        return "redirect:/profiles";
    }
}
