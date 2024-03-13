package com.accountmanagement.repo;

import com.accountmanagement.model.Costs;
import com.accountmanagement.model.enums.CostsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostsRepo extends JpaRepository<Costs, Long> {
    List<Costs> findAllByCategory(CostsCategory category);

    List<Costs> findAllByCategoryAndDate(CostsCategory category, String date);

}
