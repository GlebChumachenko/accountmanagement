package com.accountmanagement.controller.rest;

import com.accountmanagement.controller.main.Main;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rest/login")
public class LoginRestCont extends Main {
    @GetMapping
    public String login(Model model) {
        getCurrentUserAndRole(model);
        return "login";
    }
}
