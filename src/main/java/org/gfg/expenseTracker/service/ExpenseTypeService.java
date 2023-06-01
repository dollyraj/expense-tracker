package org.gfg.expenseTracker.service;

import org.gfg.expenseTracker.exceptionHandling.CustomException;
import org.gfg.expenseTracker.model.ExpenseTypes;
import org.gfg.expenseTracker.model.GenericMessages;
import org.gfg.expenseTracker.model.User;
import org.gfg.expenseTracker.model.UserStatus;
import org.gfg.expenseTracker.repository.ExpenseTypeRepository;
import org.gfg.expenseTracker.repository.UserRepository;
import org.gfg.expenseTracker.request.CreateExpenseTypeRequest;
import org.gfg.expenseTracker.response.CreateExpenseTypeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseTypeService implements ExpenseTypeServiceInterface{

    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;

    @Autowired
    private UserRepository userRepository;

    public CreateExpenseTypeResponse addExpenseType(CreateExpenseTypeRequest expenseTypeRequest) {
        ExpenseTypes expenseTypesFromDB  = expenseTypeRepository.findByExpenseType(expenseTypeRequest.getExpenseType());
       /* if(expenseTypesFromDB == null){
            ExpenseTypes expenseTypes = expenseTypeRequest.toExpenseTypes();
            expenseTypesFromDB = expenseTypeRepository.save(expenseTypes);
        }else{
            throw new CustomException("ExpenseType is already there,so not adding again");
        }*/

        if(expenseTypesFromDB!=null){
            return CreateExpenseTypeResponse.builder().
                    message(GenericMessages.ENTRY_ALREADY_PRESENT.getMessage()).build();
        }

        ExpenseTypes expenseTypesInDb = expenseTypeRequest.toExpenseTypes();

        //get current logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user =  (User) authentication.getPrincipal();

        User userFromDB = userRepository.findByEmail(user.getEmail());
        System.out.println(userFromDB);
        // handle ->  user is not present -> say -> then we will create an entry
        //User userFromDB = userRepository.findByEmail(expenseTypesFromDB.getCreatedBy());

       /* if(userFromDB == null){
            User user = User.builder().
                    email(expenseTypeRequest.getUserEmail()).
                    userStatus(UserStatus.ACTIVE).
                    build();
            userFromDB = userRepository.save(user);
        }*/

        //checking user status

        if(userFromDB!=null && !userFromDB.getUserStatus().equals(UserStatus.ACTIVE)){

            throw new CustomException("User should be in ACTIVE state to add expense types.Current user status is:"+userFromDB.getUserStatus());
        }

        if(userFromDB != null){
            expenseTypesInDb.setCreatedBy(userFromDB);
        }else{
            return CreateExpenseTypeResponse.builder().message(GenericMessages.USER_NOT_PRESENT.getMessage()).build();
        }

        try{
            expenseTypesInDb=expenseTypeRepository.save(expenseTypesInDb);

        }catch (Exception e){
            e.printStackTrace();
            return CreateExpenseTypeResponse.builder().message(GenericMessages.EXPENSE_TYPE_ADDITION_FAILURE.getMessage()).build();
        }
        return CreateExpenseTypeResponse.builder().
                message(GenericMessages.EXPENSE_TYPE_ADDITION_SUCCESS.getMessage()).
                expenseId(expenseTypesInDb.getId())
                .userId(userFromDB.getId()).
                build();

      /*  CreateExpenseTypeResponse createExpenseTypeResponse = CreateExpenseTypeResponse.builder().
                expenseId(expenseTypesFromDB.getId()).
                userId(userFromDB.getId()).
                build();*/

       // return createExpenseTypeResponse;
    }


    public List<ExpenseTypes> getAllExpenseTypes() {
        List<ExpenseTypes> list=expenseTypeRepository.findAll();
        if(list.isEmpty()){
            throw new CustomException("No expense type is available");
        }
        return list;
    }
}
// cost >= 200 filter
// expenseDate yes filter
// notes -> yes filter
// expensetype
// 1 option i should create different method for all