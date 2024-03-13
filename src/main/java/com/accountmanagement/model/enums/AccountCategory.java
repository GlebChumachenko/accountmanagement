package com.accountmanagement.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountCategory {
    SELL("Продажа"),
    BUY("Закупка"),
    ;

    private final String name;
}

