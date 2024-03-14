package com.accountmanagement.controller.rest;

import com.accountmanagement.controller.main.Main;
import com.accountmanagement.dto.StatsDto;
import com.accountmanagement.model.Accounts;
import com.accountmanagement.model.Costs;
import com.accountmanagement.model.enums.AccountCategory;
import com.accountmanagement.model.enums.CostsCategory;
import com.accountmanagement.repo.AccountsRepo;
import com.accountmanagement.repo.CostsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rest/stats")
public class StatsRestCont extends Main {
    @Autowired
    private AccountsRepo accountsRepo;
    @Autowired
    private CostsRepo costsRepo;

    @GetMapping
    public ResponseEntity<StatsDto> stats() {

        AccountCategory[] accountCategories = AccountCategory.values();
        String[] accountString = new String[accountCategories.length];
        float[] accountFloat = new float[accountCategories.length];

        for (int i = 0; i < accountCategories.length; i++) {
            accountString[i] = accountCategories[i].getName();
            for (Accounts account : accountsRepo.findAllByCategory(accountCategories[i])) {
                accountFloat[i] += account.getSum();
            }
        }

        CostsCategory[] costsCategories = CostsCategory.values();
        String[] costString = new String[costsCategories.length];
        float[] costFloat = new float[costsCategories.length];

        for (int i = 0; i < costsCategories.length; i++) {
            costString[i] = costsCategories[i].getName();
            for (Costs cost : costsRepo.findAllByCategory(costsCategories[i])) {
                costFloat[i] += cost.getSum();
            }
        }

        return ResponseEntity.ok(StatsDto
                .builder()
                .accountCategory(accountString)
                .accountValue(accountFloat)
                .costCategory(costString)
                .costValue(costFloat)
                .build());

    }
}
