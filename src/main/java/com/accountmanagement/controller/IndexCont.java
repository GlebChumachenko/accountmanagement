package com.accountmanagement.controller;

import com.accountmanagement.controller.main.Main;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexCont extends Main {
    @GetMapping
    public String index1() {
        return "redirect:/profile";
    }

    @GetMapping("/")
    public String index2() {
        return "redirect:/profile";
    }

    @GetMapping("/index")
    public String index3() {
        return "redirect:/profile";
    }
}
