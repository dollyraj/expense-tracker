package org.gfg.expenseTracker.service;

import org.gfg.expenseTracker.model.TxnFilterOperators;
import org.gfg.expenseTracker.model.TxnFilterType;
import org.gfg.expenseTracker.request.CreateTxnRequest;
import org.gfg.expenseTracker.response.AnalyticalResponse;
import org.gfg.expenseTracker.response.CreateTxnResponse;
import org.gfg.expenseTracker.response.TxnSearchResponse;

import java.text.ParseException;
import java.util.List;

public interface TxnServiceInterface {
    public CreateTxnResponse addUserTxn(CreateTxnRequest createTxnRequest);
    List<TxnSearchResponse> fetchUserTxnDetails(TxnFilterType txnFilterType, TxnFilterOperators operators, String[] values)throws ParseException;
    AnalyticalResponse fetchCalculatedResponse();
}
