package org.gfg.expenseTracker.repository;

import org.gfg.expenseTracker.model.TxnDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface TxnDetailsRepository extends JpaRepository<TxnDetails, Integer> {
    // i am using standard way of writing the methods



    public List<TxnDetails> findByExpenditureAmount(Double value);

    public List<TxnDetails> findByExpenditureAmountLessThan(Double value);
    public List<TxnDetails> findByExpenditureAmountLessThanEqual(Double value);

    public  List<TxnDetails> findByExpenditureAmountGreaterThan(Double value);
    public  List<TxnDetails> findByExpenditureAmountGreaterThanEqual(Double value);

    public List<TxnDetails> findByExpenditureAmountBetween(Double value1,Double val2);

    public List<TxnDetails> findByExpenseDate(Date date);
    public List<TxnDetails> findByExpenseDateLessThan(Date date);
    public List<TxnDetails> findByExpenseDateLessThanEqual(Date date);

    public List<TxnDetails> findByExpenseDateGreaterThan(Date date);
    public List<TxnDetails> findByExpenseDateGreaterThanEqual(Date parse);
    public List<TxnDetails> findByExpenseDateBetween(Date date1,Date date2);


    // we don have support for getting aggregated in jpa
    @Query(value = "select SUM(t.expenditure_amount) from txn_details t inner join user u where t.user_id = u.id and t.expense_date >=:date and u.id = :userId", nativeQuery = true )
    public Double getAggregatedData(LocalDate date, Integer userId);

    public List<TxnDetails> findByExpenseTypesExpenseType(String valueOf);



}
