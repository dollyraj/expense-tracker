package org.gfg.expenseTracker.service;

import org.gfg.expenseTracker.exceptionHandling.NoResourceFoundException;
import org.gfg.expenseTracker.model.*;
import org.gfg.expenseTracker.repository.ExpenseTypeRepository;
import org.gfg.expenseTracker.repository.TxnDetailsRepository;
import org.gfg.expenseTracker.repository.UserRepository;
import org.gfg.expenseTracker.request.CreateTxnRequest;
import org.gfg.expenseTracker.response.AnalyticalResponse;
import org.gfg.expenseTracker.response.CreateTxnResponse;
import org.gfg.expenseTracker.response.TxnSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TxnService {

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

        User userFromDb = userRepository.findByEmail(createTxnRequest.getUserEmail());

        // custom exception
        if(userFromDb == null){
            User user = User.builder().
                    email(createTxnRequest.getUserEmail()).
                    userStatus(UserStatus.ACTIVE).
                    build();
            userFromDb = userRepository.save(user);
        }

        ExpenseTypes expenseTypesFromDb  = expenseTypeRepository.findByExpenseType(createTxnRequest.getExpenseType());

        if(expenseTypesFromDb == null){
            ExpenseTypes expenseTypes = ExpenseTypes.builder().
                    expenseType(createTxnRequest.getExpenseType()).
                    createdBy(createTxnRequest.getUserEmail()).build();
            expenseTypesFromDb = expenseTypeRepository.save(expenseTypes);
        }

        TxnDetails txnDetails = createTxnRequest.toTxnDetails(createTxnRequest);
        txnDetails.setUser(userFromDb);
        txnDetails.setExpenseTypes(expenseTypesFromDb);
        TxnDetails txnDetailsFromDb = txnDetailsRepository.save(txnDetails);

        return  CreateTxnResponse.builder().userId(userFromDb.getId()).expenseId(expenseTypesFromDb.getId()).build();

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
                    case LESS_THAN_EQUALS:
                        txnDetailsList.addAll(txnDetailsRepository.findByExpenditureAmountLessThanEqual(Double.valueOf(values[0])));
                        break;
                    case GREATER_THAN_EQUALS:
                        txnDetailsList.addAll(txnDetailsRepository.findByExpenditureAmountGreaterThanEqual(Double.valueOf(values[0])));
                        break;
                }
                break;
            case EXPENSE_DATE:
                switch (operators){
                    case LESS_THAN_EQUALS:
                        txnDetailsList.addAll(txnDetailsRepository.findByExpenseDateLessThanEqual(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(values[0])));
                        break;
                    case GREATER_THAN_EQUALS:
                        txnDetailsList.addAll(txnDetailsRepository.findByExpenseDateGreaterThanEqual(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(values[0])));
                        break;
                    case EQUALS:
                        txnDetailsList.addAll(txnDetailsRepository.findByExpenseDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(values[0])));
                        break;
                }
                break;
        }
        list = convertToSearchResponse(txnDetailsList);

       if(list.isEmpty()){
           throw new NoResourceFoundException("No transaction is found");
       }
        return list;
    }

    private List<TxnSearchResponse> convertToSearchResponse(List<TxnDetails> txnDetailsList) {
        List<TxnSearchResponse> txnSearchResponses = new ArrayList<>();

        for(int i= 0; i< txnDetailsList.size();i++){
            TxnSearchResponse txnSearchResponse = TxnSearchResponse.builder().
                    user(txnDetailsList.get(i).getUser()).
                    expenditureAmount(txnDetailsList.get(i).getExpenditureAmount()).
                    expenseDate(txnDetailsList.get(i).getExpenseDate().toString()).
                expenseType(txnDetailsList.get(i).getExpenseTypes().getExpenseType()).build();
            txnSearchResponses.add(txnSearchResponse);
        }
        return txnSearchResponses;
    }

    public AnalyticalResponse fetchCalculatedResponse(String email) {

        // form the date
        LocalDate todayDate = LocalDate.now();
        LocalDate sevenDayBackDate = LocalDate.now().minusDays(7);
        LocalDate fifteenDayBackDate= LocalDate.now().minusDays(15);

        User user =userRepository.findByEmail(email);
        Double oneDayAmount = txnDetailsRepository.getAggregatedData(todayDate, user.getId());
        Double sevenDayAmount = txnDetailsRepository.getAggregatedData(sevenDayBackDate, user.getId());
        Double fifteenDayAmount=txnDetailsRepository.getAggregatedData(fifteenDayBackDate, user.getId());
        return AnalyticalResponse.builder().
                userEmail(email).
        oneDayAmount(oneDayAmount).sevenDayAmount(sevenDayAmount).
                fifteenDayAmount(fifteenDayAmount).build();


        // select sum(expenditure_cost)  from txnDetails where expenseDate >= date and userid = userid
    }
}

// 1 cache ? it is going to db
// AOP
// test cases
// security


// MANY to MANY

// 1 txn can have multiple txntype  food, shopping, travel -> many to many
// 1 txntype can have multiple txn
