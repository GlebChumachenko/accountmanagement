package com.accountmanagement.controller.rest;

import com.accountmanagement.controller.main.Main;
import com.accountmanagement.model.Accounts;
import com.accountmanagement.model.enums.AccountCategory;
import com.accountmanagement.repo.AccountsRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/accounts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountsRestCont extends Main {

    AccountsRepo accountsRepo;

    @GetMapping
    public ResponseEntity<List<Accounts>> accounts() {
        return ResponseEntity.ok(accountsRepo.findAll());
    }

//    @GetMapping("/report")
//    public String accountsReport(Model model) {
//        getCurrentUserAndRole(model);
//        model.addAttribute("accounts", accountsRepo.findAll());
//        return "accounts_report";
//    }

    @PostMapping("/add")
    public ResponseEntity<Map<String,Accounts>> accountAdd(@RequestParam String name, @RequestParam float sum, @RequestParam String date, @RequestParam AccountCategory category) {
        return ResponseEntity.ok(Collections.singletonMap("New Account: ", accountsRepo.save(new Accounts(name, sum, date, category))));
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<Map<String,Accounts>> accountEdit(@RequestParam String name, @RequestParam float sum, @RequestParam String date, @RequestParam AccountCategory category, @PathVariable Long id) {
        Accounts account = accountsRepo.getReferenceById(id);
        account.set(name, sum, date, category);
        Accounts savedAccount = accountsRepo.save(account);
        return ResponseEntity.ok(Collections.singletonMap("Updated Account: ", savedAccount));
    }

    @GetMapping("/{id}/delete")
    public ResponseEntity<?> accountEdit(@PathVariable Long id) {
        accountsRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}