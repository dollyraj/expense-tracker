package org.gfg.expenseTracker.service;

import org.gfg.expenseTracker.exceptionHandling.CustomException;
import org.gfg.expenseTracker.model.*;
import org.gfg.expenseTracker.repository.ExpenseTypeRepository;
import org.gfg.expenseTracker.repository.TxnDetailsRepository;
import org.gfg.expenseTracker.repository.UserRepository;
import org.gfg.expenseTracker.request.CreateTxnRequest;
import org.gfg.expenseTracker.response.AnalyticalResponse;
import org.gfg.expenseTracker.response.CreateExpenseTypeResponse;
import org.gfg.expenseTracker.response.CreateTxnResponse;
import org.gfg.expenseTracker.response.TxnSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TxnService implements TxnServiceInterface{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;

    @Autowired
    private TxnDetailsRepository txnDetailsRepository;

    public CreateTxnResponse addUserTxn(CreateTxnRequest createTxnRequest) {

        // user if not exist -> email
        // expense type  ->  if it will not be there
        // txn in txndetails

       // User userFromDb = userRepository.findByEmail(createTxnRequest.getUserEmail());
        //get current logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user =  (User) authentication.getPrincipal();

        User userFromDb=userRepository.findByEmail(user.getEmail());

       // System.out.println(userFromDb);
        // custom exception
       /* if(userFromDb == null){
            User user = User.builder().
                    email(createTxnRequest.getUserEmail()).
                    userStatus(UserStatus.ACTIVE).
                    build();
            userFromDb = userRepository.save(user);
        }*/





        //checking user status
        if(userFromDb!=null && !userFromDb.getUserStatus().equals(UserStatus.ACTIVE)){
            throw new CustomException("User should be in ACTIVE state to add transactions.Current user status is:"+userFromDb.getUserStatus());
        }



       //if expense type does not exist , create that expense
        ExpenseTypes expenseTypesFromDb  = expenseTypeRepository.findByExpenseType(createTxnRequest.getExpenseType());

        if(expenseTypesFromDb == null){
            ExpenseTypes expenseTypes = ExpenseTypes.builder().
                    expenseType(createTxnRequest.getExpenseType()).
                    createdBy(userFromDb).build();
            expenseTypesFromDb = expenseTypeRepository.save(expenseTypes);
        }

        TxnDetails txnDetailsInDb = createTxnRequest.toTxnDetails(createTxnRequest);
        if(userFromDb != null){
            txnDetailsInDb.setCreatedBy(userFromDb);
            txnDetailsInDb.setExpenseTypes(expenseTypesFromDb);

        }else {
            return CreateTxnResponse.builder().message(GenericMessages.USER_NOT_PRESENT.getMessage()).build();
        }

        // txnDetailsInDb.setCreatedBy(userFromDb);
        //txnDetailsInDb.setExpenseTypes(expenseTypesFromDb);
        try{
            txnDetailsInDb=txnDetailsRepository.save(txnDetailsInDb);

        }catch (Exception e){
            e.printStackTrace();
            return CreateTxnResponse.builder().message(GenericMessages.TXN_ADDITION_FAILURE.getMessage()).build();
        }
        return CreateTxnResponse.builder().
                message(GenericMessages.TXN_ADDITION_SUCCESS.getMessage())
                .expenseId(expenseTypesFromDb.getId())
                .userId(userFromDb.getId()).
                build();


       /* TxnDetails txnDetails = createTxnRequest.toTxnDetails(createTxnRequest);
        txnDetails.setUser(userFromDb);
        txnDetails.setExpenseTypes(expenseTypesFromDb);
        TxnDetails txnDetailsFromDb = txnDetailsRepository.save(txnDetails);*/

       // return  CreateTxnResponse.builder().userId(userFromDb.getId()).expenseId(expenseTypesFromDb.getId()).build();

    }

    public List<TxnSearchResponse> fetchUserTxnDetails(TxnFilterType txnFilterType, TxnFilterOperators operators, String[] values) throws ParseException {
        List<TxnSearchResponse> list = new ArrayList<>();
        List<TxnDetails> txnDetailsList = new ArrayList<>();


        switch (txnFilterType) {
            case EXPENSE_TYPE: // food , travel >= , <=

                  txnDetailsList.addAll(txnDetailsRepository.findByExpenseTypesExpenseType(String.valueOf(values[0])));
                break;
            case EXPENDITURE_AMOUNT:
                switch (operators){
                    case EQUALS:
                        txnDetailsList.addAll(txnDetailsRepository.findByExpenditureAmount(Double.valueOf(values[0])));
                        break;
                    case LESS_THAN:
                        txnDetailsList.addAll(txnDetailsRepository.findByExpenditureAmountLessThan(Double.valueOf(values[0])));
                        break;
                    case LESS_THAN_EQUALS:
                        txnDetailsList.addAll(txnDetailsRepository.findByExpenditureAmountLessThanEqual(Double.valueOf(values[0])));
                        break;
                    case GREATER_THAN:
                        txnDetailsList.addAll(txnDetailsRepository.findByExpenditureAmountGreaterThan(Double.valueOf(values[0])));
                        break;
                    case GREATER_THAN_EQUALS:
                        txnDetailsList.addAll(txnDetailsRepository.findByExpenditureAmountGreaterThanEqual(Double.valueOf(values[0])));
                        break;
                    case BETWEEN:
                        txnDetailsList.addAll(txnDetailsRepository.findByExpenditureAmountBetween(Double.valueOf(values[0]),Double.valueOf(values[1])));
                        break;
                }
                break;
            case EXPENSE_DATE:
                switch (operators){
                    case LESS_THAN:
                        txnDetailsList.addAll(txnDetailsRepository.findByExpenseDateLessThan(new SimpleDateFormat("dd-MM-yyyy").parse(values[0])));
                        break;
                    case LESS_THAN_EQUALS:
                        txnDetailsList.addAll(txnDetailsRepository.findByExpenseDateLessThanEqual(new SimpleDateFormat("dd-MM-yyyy").parse(values[0])));
                        break;
                    case GREATER_THAN:
                        txnDetailsList.addAll(txnDetailsRepository.findByExpenseDateGreaterThan(new SimpleDateFormat("dd-MM-yyyy").parse(values[0])));
                        break;
                    case GREATER_THAN_EQUALS:
                        txnDetailsList.addAll(txnDetailsRepository.findByExpenseDateGreaterThanEqual(new SimpleDateFormat("dd-MM-yyyy").parse(values[0])));
                        break;
                    case EQUALS:
                        //"yyyy-MM-dd hh:mm:ss"
                        txnDetailsList.addAll(txnDetailsRepository.findByExpenseDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(values[0])));
                        break;
                    case BETWEEN:
                        txnDetailsList.addAll(txnDetailsRepository.findByExpenseDateBetween(new SimpleDateFormat("dd-MM-yyyy").parse(values[0]),new SimpleDateFormat("dd-MM-yyyy").parse(values[1])));
                        break;
                }
                break;//2023-05-30T08:06:26
        }
        if(txnDetailsList.isEmpty()){
            throw new CustomException("No transaction is found");
        }
        list = convertToSearchResponse(txnDetailsList);
//
//       if(list.isEmpty()){
//           throw new CustomException("No transaction is found");
//       }
        return list;
    }

    private List<TxnSearchResponse> convertToSearchResponse(List<TxnDetails> txnDetailsList) {
        List<TxnSearchResponse> txnSearchResponses = new ArrayList<>();

        for(int i= 0; i< txnDetailsList.size();i++){
            TxnSearchResponse txnSearchResponse = TxnSearchResponse.builder().
                    user(txnDetailsList.get(i).getCreatedBy()).
                    expenditureAmount(txnDetailsList.get(i).getExpenditureAmount()).
                    expenseDate(txnDetailsList.get(i).getExpenseDate().toString()).
                expenseType(txnDetailsList.get(i).getExpenseTypes().getExpenseType()).build();
            txnSearchResponses.add(txnSearchResponse);
        }
        return txnSearchResponses;
    }

    public AnalyticalResponse fetchCalculatedResponse() {

        //get current logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String email= user.getEmail();
        System.out.println(user);
        // form the date
        LocalDate todayDate = LocalDate.now();
        LocalDate sevenDayBackDate = LocalDate.now().minusDays(7);
        LocalDate fifteenDayBackDate= LocalDate.now().minusDays(15);

        User userFromDb =userRepository.findByEmail(email);
       // System.out.println(userFromDb.getTxnDetailsList());//check it why giving infinite loop
        //because of @ToString

        Double oneDayAmount = txnDetailsRepository.getAggregatedData(todayDate, userFromDb.getId());
        if(oneDayAmount==null)
            oneDayAmount=0.0;
        Double sevenDayAmount = txnDetailsRepository.getAggregatedData(sevenDayBackDate, userFromDb.getId());
        if(sevenDayAmount==null)
            sevenDayAmount=0.0;
        Double fifteenDayAmount=txnDetailsRepository.getAggregatedData(fifteenDayBackDate, userFromDb.getId());
        if(fifteenDayAmount==null)
            fifteenDayAmount=0.0;
        return AnalyticalResponse.builder().
                userEmail(email).
        oneDayAmount(oneDayAmount).sevenDayAmount(sevenDayAmount).
                fifteenDayAmount(fifteenDayAmount).build();


        // select sum(expenditure_cost)  from txnDetails where expenseDate >= date and userid = userid
    }


    public List<TxnSearchResponse> fetchLoggedInUserTxnDetails(TxnFilterType txnFilterType, TxnFilterOperators operators, String[] values) throws ParseException {
        List<TxnSearchResponse> list = new ArrayList<>();
        List<TxnDetails> txnDetailsList = new ArrayList<>();

        //get current logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user =  (User) authentication.getPrincipal();

        User userFromDB = userRepository.findByEmail(user.getEmail());
        Integer userId= userFromDB.getId();
        switch (txnFilterType) {
            case EXPENSE_TYPE: // food , travel >= , <=

                txnDetailsList.addAll(txnDetailsRepository.findBycreatedByIdAndExpenseTypesExpenseType(userId,String.valueOf(values[0])));
                break;
            case EXPENDITURE_AMOUNT:
                switch (operators){
                    case EQUALS:
                        txnDetailsList.addAll(txnDetailsRepository.findBycreatedByIdAndExpenditureAmount(userId,Double.valueOf(values[0])));
                        break;
                    case LESS_THAN:
                        txnDetailsList.addAll(txnDetailsRepository.findBycreatedByIdAndExpenditureAmountLessThan(userId,Double.valueOf(values[0])));
                        break;
                    case LESS_THAN_EQUALS:
                        txnDetailsList.addAll(txnDetailsRepository.findBycreatedByIdAndExpenditureAmountLessThanEqual(userId,Double.valueOf(values[0])));
                        break;
                    case GREATER_THAN:
                        txnDetailsList.addAll(txnDetailsRepository.findBycreatedByIdAndExpenditureAmountGreaterThan(userId,Double.valueOf(values[0])));
                        break;
                    case GREATER_THAN_EQUALS:
                        txnDetailsList.addAll(txnDetailsRepository.findBycreatedByIdAndExpenditureAmountGreaterThanEqual(userId,Double.valueOf(values[0])));
                        break;
                    case BETWEEN:
                        txnDetailsList.addAll(txnDetailsRepository.findBycreatedByIdAndExpenditureAmountBetween(userId,Double.valueOf(values[0]),Double.valueOf(values[1])));
                        break;
                }
                break;
            case EXPENSE_DATE:
                switch (operators){
                    case LESS_THAN:
                        txnDetailsList.addAll(txnDetailsRepository.findBycreatedByIdAndExpenseDateLessThan(userId,new SimpleDateFormat("dd-MM-yyyy").parse(values[0])));
                        break;
                    case LESS_THAN_EQUALS:
                        txnDetailsList.addAll(txnDetailsRepository.findBycreatedByIdAndExpenseDateLessThanEqual(userId,new SimpleDateFormat("dd-MM-yyyy").parse(values[0])));
                        break;
                    case GREATER_THAN:
                        txnDetailsList.addAll(txnDetailsRepository.findBycreatedByIdAndExpenseDateGreaterThan(userId,new SimpleDateFormat("dd-MM-yyyy").parse(values[0])));
                        break;
                    case GREATER_THAN_EQUALS:
                        txnDetailsList.addAll(txnDetailsRepository.findBycreatedByIdAndExpenseDateGreaterThanEqual(userId,new SimpleDateFormat("dd-MM-yyyy").parse(values[0])));
                        break;
                    case EQUALS:
                        //"yyyy-MM-dd hh:mm:ss"
                        txnDetailsList.addAll(txnDetailsRepository.findBycreatedByIdAndExpenseDate(userId,new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(values[0])));
                        break;
                    case BETWEEN:
                        txnDetailsList.addAll(txnDetailsRepository.findBycreatedByIdAndExpenseDateBetween(userId,new SimpleDateFormat("dd-MM-yyyy").parse(values[0]),new SimpleDateFormat("dd-MM-yyyy").parse(values[1])));
                        break;
                }
                break;//2023-05-30T08:06:26
        }
        if(txnDetailsList.isEmpty()){
            throw new CustomException("No transaction is found");
        }
        list = convertToSearchResponse(txnDetailsList);
//
//       if(list.isEmpty()){
//           throw new CustomException("No transaction is found");
//       }
        return list;
    }
}

// 1 cache ? it is going to db
// AOP
// test cases
// security


// MANY to MANY

// 1 txn can have multiple txntype  food, shopping, travel -> many to many
// 1 txntype can have multiple txn
