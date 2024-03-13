package com.accountmanagement.controller;

import com.accountmanagement.controller.main.Main;
import com.accountmanagement.model.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/profile")
public class ProfileCont extends Main {
    @GetMapping
    public String profile(Model model) {
        getCurrentUserAndRole(model);
        return "profile";
    }

    @PostMapping("/edit")
    public String profileEdit(@RequestParam String fio, @RequestParam String password) {
        Users user = getUser();
        if (!password.isEmpty()) user.setPassword(password);
        user.setFio(fio);
        usersRepo.save(user);
        return "redirect:/profile";
    }
}
