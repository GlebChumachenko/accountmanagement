package com.accountmanagement.controller;

import com.accountmanagement.controller.main.Main;
import com.accountmanagement.model.enums.AccountCategory;
import com.accountmanagement.model.enums.CostsCategory;
import com.accountmanagement.repo.AccountsRepo;
import com.accountmanagement.repo.CostsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/histogram")
public class HistogramCont extends Main {
    @Autowired
    private AccountsRepo accountsRepo;
    @Autowired
    private CostsRepo costsRepo;

    @GetMapping
    public String histogram(Model model) {
        getCurrentUserAndRole(model);
        return "histogram";
    }

    @GetMapping("/filter")
    public String histogramFilter(Model model, @RequestParam String start, @RequestParam String finish) {
        getCurrentUserAndRole(model);
        model.addAttribute("start", start);
        model.addAttribute("finish", finish);

        String[] s = start.split("-");
        String[] f = finish.split("-");

        LocalDateTime dateStart = LocalDateTime.of(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), 0, 0);
        LocalDateTime dateFinish = LocalDateTime.of(Integer.parseInt(f[0]), Integer.parseInt(f[1]), Integer.parseInt(f[2]), 0, 0);

        String[] dates = new String[(int) (Duration.between(dateStart, dateFinish).toDays() + 1)];
        float[] floatAccountBuy = new float[dates.length];
        float[] floatAccountSell = new float[dates.length];
        float[] floatCostSalary = new float[dates.length];
        float[] floatCostMarketing = new float[dates.length];
        float[] floatCostOther = new float[dates.length];

        for (int i = 0; i < dates.length; i++) {
            dates[i] = dateStart.toString().substring(0, 10);

            floatAccountBuy[i] = accountsRepo.findAllByCategoryAndDate(AccountCategory.BUY, dates[i]).stream().reduce(0f, (res, account) -> res + account.getSum(), Float::sum);
            floatAccountSell[i] = accountsRepo.findAllByCategoryAndDate(AccountCategory.SELL, dates[i]).stream().reduce(0f, (res, account) -> res + account.getSum(), Float::sum);
            floatCostSalary[i] = costsRepo.findAllByCategoryAndDate(CostsCategory.SALARY, dates[i]).stream().reduce(0f, (res, account) -> res + account.getSum(), Float::sum);
            floatCostMarketing[i] = costsRepo.findAllByCategoryAndDate(CostsCategory.MARKETING, dates[i]).stream().reduce(0f, (res, account) -> res + account.getSum(), Float::sum);
            floatCostOther[i] = costsRepo.findAllByCategoryAndDate(CostsCategory.OTHER, dates[i]).stream().reduce(0f, (res, account) -> res + account.getSum(), Float::sum);

            dateStart = dateStart.plusDays(1);
        }

        model.addAttribute("dates", dates);
        model.addAttribute("floatAccountBuy", floatAccountBuy);
        model.addAttribute("floatAccountSell", floatAccountSell);
        model.addAttribute("floatCostSalary", floatCostSalary);
        model.addAttribute("floatCostMarketing", floatCostMarketing);
        model.addAttribute("floatCostOther", floatCostOther);

        return "histogram";
    }
}
