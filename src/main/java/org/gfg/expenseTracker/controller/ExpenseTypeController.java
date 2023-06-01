package org.gfg.expenseTracker.controller;

import org.gfg.expenseTracker.model.ExpenseTypes;
import org.gfg.expenseTracker.model.User;
import org.gfg.expenseTracker.request.CreateExpenseTypeRequest;
import org.gfg.expenseTracker.response.CreateExpenseTypeResponse;
import org.gfg.expenseTracker.response.GenericResponse;
import org.gfg.expenseTracker.service.ExpenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/expenseTracker")
public class ExpenseTypeController {

    @Autowired
    private ExpenseTypeService expenseTypeService;

    //add new expense type
    @PostMapping("/addExpenseType")
    public GenericResponse<CreateExpenseTypeResponse> addExpenseType(@Valid @RequestBody CreateExpenseTypeRequest expenseTypeRequest){
        CreateExpenseTypeResponse createExpenseTypeResponse = expenseTypeService.addExpenseType(expenseTypeRequest);
        GenericResponse genericResponse = GenericResponse.builder().
                code(HttpStatus.OK.value()).
                message("Expense Type details has been saved").
                statusCode(0).data(createExpenseTypeResponse)
                .build();
        return genericResponse;
    }

    //get all expense types
    @GetMapping("/getAllExpenseType")
    public GenericResponse<ExpenseTypes> getAllExpenseTypes(){
        List<ExpenseTypes> expenseTypes= expenseTypeService.getAllExpenseTypes();
        GenericResponse genericResponse = GenericResponse.builder().
                code(HttpStatus.OK.value()).
                message("Expense types details has been fetched").
                statusCode(0).data(expenseTypes).
                build();
        return genericResponse;

    }

}
