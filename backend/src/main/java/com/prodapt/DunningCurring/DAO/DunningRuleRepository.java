package com.prodapt.DunningCurring.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prodapt.DunningCurring.Entity.DunningRule;

import java.util.List;

public interface DunningRuleRepository extends JpaRepository<DunningRule, Long> {

    List<DunningRule> findByActiveTrueOrderByOverdueDaysAsc();
}

