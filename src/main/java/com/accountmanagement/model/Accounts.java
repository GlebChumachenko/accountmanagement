package com.accountmanagement.model;

import com.accountmanagement.model.enums.AccountCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Accounts {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;
    private float sum;
    private String date;
    @Enumerated(EnumType.STRING)
    private AccountCategory category;

    public Accounts(String name, float sum, String date, AccountCategory category) {
        this.name = name;
        this.sum = sum;
        this.date = date;
        this.category = category;
    }

    public void set(String name, float sum, String date, AccountCategory category) {
        this.name = name;
        this.sum = sum;
        this.date = date;
        this.category = category;
    }
}