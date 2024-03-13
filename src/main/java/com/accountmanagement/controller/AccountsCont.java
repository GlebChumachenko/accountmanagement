package com.accountmanagement.controller;

import com.accountmanagement.controller.main.Main;
import com.accountmanagement.model.Accounts;
import com.accountmanagement.model.enums.AccountCategory;
import com.accountmanagement.repo.AccountsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/accounts")
public class AccountsCont extends Main {
    @Autowired
    private AccountsRepo accountsRepo;

    @GetMapping
    public String accounts(Model model) {
        getCurrentUserAndRole(model);
        model.addAttribute("categories", AccountCategory.values());
        model.addAttribute("accounts", accountsRepo.findAll());
        return "accounts";
    }

    @GetMapping("/report")
    public String accountsReport(Model model) {
        getCurrentUserAndRole(model);
        model.addAttribute("accounts", accountsRepo.findAll());
        return "accounts_report";
    }

    @PostMapping("/add")
    public String accountAdd(@RequestParam String name, @RequestParam float sum, @RequestParam String date, @RequestParam AccountCategory category) {
        accountsRepo.save(new Accounts(name, sum, date, category));
        return "redirect:/accounts";
    }

    @PostMapping("/{id}/edit")
    public String accountEdit(@RequestParam String name, @RequestParam float sum, @RequestParam String date, @RequestParam AccountCategory category, @PathVariable Long id) {
        Accounts account = accountsRepo.getReferenceById(id);
        account.set(name, sum, date, category);
        accountsRepo.save(account);
        return "redirect:/accounts";
    }

    @GetMapping("/{id}/delete")
    public String accountEdit(@PathVariable Long id) {
        accountsRepo.deleteById(id);
        return "redirect:/accounts";
    }
}