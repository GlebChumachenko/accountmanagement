package com.accountmanagement.controller.rest;

import com.accountmanagement.controller.main.Main;
import com.accountmanagement.model.Costs;
import com.accountmanagement.model.enums.CostsCategory;
import com.accountmanagement.repo.CostsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rest/costs")
public class CostsRestCont extends Main {
    @Autowired
    private CostsRepo costsRepo;

    @GetMapping
    public ResponseEntity<List<Costs>> costs() {
        return ResponseEntity.ok(costsRepo.findAll());
    }

//    @GetMapping("/report")
//    public String costsReport(Model model) {
//        getCurrentUserAndRole(model);
//        model.addAttribute("costs", costsRepo.findAll());
//        return "costs_report";
//    }

    @PostMapping("/add")
    public ResponseEntity<Costs> costAdd(@RequestParam String name, @RequestParam float sum, @RequestParam String date, @RequestParam CostsCategory category) {
        return ResponseEntity.ok(costsRepo.save(new Costs(name, sum, date, category)));
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<Costs> costEdit(@RequestParam String name, @RequestParam float sum, @RequestParam String date, @RequestParam CostsCategory category, @PathVariable Long id) {
        Costs cost = costsRepo.getReferenceById(id);
        cost.set(name, sum, date, category);
        return ResponseEntity.ok(costsRepo.save(cost));
    }

    @GetMapping("/{id}/delete")
    public ResponseEntity<?> accountEdit(@PathVariable Long id) {
        costsRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}