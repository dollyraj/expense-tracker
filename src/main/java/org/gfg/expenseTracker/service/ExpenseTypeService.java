package org.gfg.expenseTracker.service;

import org.gfg.expenseTracker.exceptionHandling.NoResourceFoundException;
import org.gfg.expenseTracker.model.ExpenseTypes;
import org.gfg.expenseTracker.model.User;
import org.gfg.expenseTracker.model.UserStatus;
import org.gfg.expenseTracker.repository.ExpenseTypeRepository;
import org.gfg.expenseTracker.repository.UserRepository;
import org.gfg.expenseTracker.request.CreateExpenseTypeRequest;
import org.gfg.expenseTracker.response.CreateExpenseTypeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseTypeService {

    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;

    @Autowired
    private UserRepository userRepository;

    public CreateExpenseTypeResponse addExpenseType(CreateExpenseTypeRequest expenseTypeRequest) {
        ExpenseTypes expenseTypesFromDB  = expenseTypeRepository.findByExpenseType(expenseTypeRequest.getExpenseType());
        if(expenseTypesFromDB == null){
            ExpenseTypes expenseTypes = expenseTypeRequest.toExpenseTypes();
            expenseTypesFromDB = expenseTypeRepository.save(expenseTypes);
        }
        // handle ->  user is not present -> say -> then we will create an entry
        User userFromDB = userRepository.findByEmail(expenseTypesFromDB.getCreatedBy());
        if(userFromDB == null){
            User user = User.builder().
                    email(expenseTypeRequest.getUserEmail()).
                    userStatus(UserStatus.ACTIVE).
                    build();
            userFromDB = userRepository.save(user);
        }

        if(userFromDB!=null && !userFromDB.getUserStatus().equals("ACTIVE")){
            throw new NoResourceFoundException("User should be in ACTIVE state to make changes.Current user status is:"+userFromDB.getUserStatus());
        }


        CreateExpenseTypeResponse createExpenseTypeResponse = CreateExpenseTypeResponse.builder().
                expenseId(expenseTypesFromDB.getId()).
                userId(userFromDB.getId()).
                build();

        return createExpenseTypeResponse;
    }


    public List<ExpenseTypes> getAllExpenseTypes() {
        List<ExpenseTypes> list=expenseTypeRepository.findAll();
        if(list.isEmpty()){
            throw new NoResourceFoundException("No expense type is available");
        }
        return list;
    }
}
// cost >= 200 filter
// expenseDate yes filter
// notes -> yes filter
// expensetype
// 1 option i should create different method for all