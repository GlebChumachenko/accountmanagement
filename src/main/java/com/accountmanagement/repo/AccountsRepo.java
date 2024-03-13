package com.accountmanagement.repo;

import com.accountmanagement.model.Accounts;
import com.accountmanagement.model.enums.AccountCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountsRepo extends JpaRepository<Accounts, Long> {
    List<Accounts> findAllByCategory(AccountCategory category);

    List<Accounts> findAllByCategoryAndDate(AccountCategory category, String date);
}
