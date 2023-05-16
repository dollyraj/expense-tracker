package org.gfg.expenseTracker.repository;


import org.gfg.expenseTracker.model.ExpenseTypes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseTypeRepository extends JpaRepository<ExpenseTypes, Integer> {

    public ExpenseTypes findByExpenseType(String expenseType);




}
