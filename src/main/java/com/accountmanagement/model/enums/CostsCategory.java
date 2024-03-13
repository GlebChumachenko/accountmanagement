package com.accountmanagement.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CostsCategory {
    SALARY("Зарплата"),
    MARKETING("Маркетинг"),
    OTHER("Другое"),
    ;

    private final String name;
}

