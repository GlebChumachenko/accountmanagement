package com.accountmanagement.controller;

import com.accountmanagement.controller.main.Main;
import com.accountmanagement.model.Costs;
import com.accountmanagement.model.enums.CostsCategory;
import com.accountmanagement.repo.CostsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/costs")
public class CostsCont extends Main {
    @Autowired
    private CostsRepo costsRepo;

    @GetMapping
    public String costs(Model model) {
        getCurrentUserAndRole(model);
        model.addAttribute("costs", costsRepo.findAll());
        model.addAttribute("categories", CostsCategory.values());
        return "costs";
    }

    @GetMapping("/report")
    public String costsReport(Model model) {
        getCurrentUserAndRole(model);
        model.addAttribute("costs", costsRepo.findAll());
        return "costs_report";
    }

    @PostMapping("/add")
    public String costAdd(@RequestParam String name, @RequestParam float sum, @RequestParam String date, @RequestParam CostsCategory category) {
        costsRepo.save(new Costs(name, sum, date, category));
        return "redirect:/costs";
    }

    @PostMapping("/{id}/edit")
    public String costEdit(@RequestParam String name, @RequestParam float sum, @RequestParam String date, @RequestParam CostsCategory category, @PathVariable Long id) {
        Costs cost = costsRepo.getReferenceById(id);
        cost.set(name, sum, date, category);
        costsRepo.save(cost);
        return "redirect:/costs";
    }

    @GetMapping("/{id}/delete")
    public String costEdit(@PathVariable Long id) {
        costsRepo.deleteById(id);
        return "redirect:/costs";
    }
}