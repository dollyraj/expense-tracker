package org.gfg.expenseTracker.service;

import org.gfg.expenseTracker.model.ExpenseTypes;
import org.gfg.expenseTracker.request.CreateExpenseTypeRequest;
import org.gfg.expenseTracker.response.CreateExpenseTypeResponse;

import java.util.List;

public interface ExpenseTypeServiceInterface {
     CreateExpenseTypeResponse addExpenseType(CreateExpenseTypeRequest expenseTypeRequest);
     List<ExpenseTypes> getAllExpenseTypes();
}
