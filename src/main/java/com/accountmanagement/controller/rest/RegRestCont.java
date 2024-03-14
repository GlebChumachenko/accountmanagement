package com.accountmanagement.controller.rest;

import com.accountmanagement.controller.main.Main;
import com.accountmanagement.dto.OperationResponse;
import com.accountmanagement.model.Users;
import com.accountmanagement.model.enums.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/rest/reg")
public class RegRestCont extends Main {
    @GetMapping
    public String reg(Model model) {
        getCurrentUserAndRole(model);
        return "reg";
    }

    @PostMapping
    public ResponseEntity<OperationResponse> reg(@RequestParam String fio, @RequestParam String username, @RequestParam String password) {
        if (usersRepo.findAll().isEmpty()) {
            usersRepo.save(new Users(fio, username, password, Role.ADMIN));
            return ResponseEntity.ok(OperationResponse.builder().status(OperationResponse.Status.SUCCESS).build());
        }

        if (usersRepo.findByUsername(username) != null) {
            String msg = "Пользователь таким именем уже существует!";
            return new ResponseEntity<>(
                    OperationResponse
                    .builder()
                    .status(OperationResponse.Status.FAILED)
                    .message(msg)
                    .build(),
                    HttpStatus.CONFLICT
            );
        }

        usersRepo.save(new Users(fio, username, password, Role.MANAGER));

        return ResponseEntity.ok(OperationResponse.builder().status(OperationResponse.Status.SUCCESS).build());
    }
}
