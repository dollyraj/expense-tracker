package org.gfg.expenseTracker.controller;

import org.gfg.expenseTracker.model.TxnFilterOperators;
import org.gfg.expenseTracker.model.TxnFilterType;
import org.gfg.expenseTracker.request.CreateTxnRequest;
import org.gfg.expenseTracker.response.AnalyticalResponse;
import org.gfg.expenseTracker.response.CreateTxnResponse;
import org.gfg.expenseTracker.response.GenericResponse;
import org.gfg.expenseTracker.response.TxnSearchResponse;
import org.gfg.expenseTracker.service.TxnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/v1/expenseTracker")
public class TxnController {

    @Autowired
    private TxnService txnService;

    // add new transaction
    @PostMapping("/addUserTxn")
    public GenericResponse<CreateTxnResponse> addUserTxn(@Valid @RequestBody CreateTxnRequest createTxnRequest){
        CreateTxnResponse createTxnResponse = txnService.addUserTxn(createTxnRequest);
        GenericResponse genericResponse = GenericResponse.builder().
                code(HttpStatus.OK.value()).
                message("Transaction details has been saved").
                statusCode(0).data(createTxnResponse)
                .build();
        return genericResponse;

    }

   // custom method -> filter all the things
    // cost if cost == something , cost >= something , cost <= something
    // expenseType expenseType == this
    // expenseDate expenseDate == this

    // get search results
    @GetMapping("/fetchTxn")
    public GenericResponse<List<TxnSearchResponse>> fetchUserTxnDetails(@RequestParam("filter")TxnFilterType txnFilterType,
                                                                        @RequestParam("operator")TxnFilterOperators operators,
                                                                        @RequestParam("values") String value) throws ParseException {

        //  create an array from comma separated
        String[] values = value.split(",");
        List<TxnSearchResponse> listOfTxnSearchResp = txnService.fetchUserTxnDetails(txnFilterType,operators, values);
        GenericResponse genericResponse = GenericResponse.builder().
                code(HttpStatus.OK.value()).
                message("Search result is following:").
                statusCode(0).data(listOfTxnSearchResp)
                .build();
        return genericResponse;
    }

    // api -> one day expense, 7 day expense, 15 days
    // fetch calculated expense
    @GetMapping("/fetchCalculatedResults")                            //@RequestParam("email") String email
    public GenericResponse<AnalyticalResponse> fetchCalculatedResponse(){
        AnalyticalResponse analyticalResponse = txnService.fetchCalculatedResponse();
        GenericResponse genericResponse = GenericResponse.builder().
                code(HttpStatus.OK.value()).
                message("Calculated result has been fetched").
                statusCode(0).data(analyticalResponse)
                .build();
        return genericResponse;

    }

    // if we don't have any business logic -> repository

    @GetMapping("/fetchUserTxn")

    public GenericResponse<List<TxnSearchResponse>> fetchTxnDetails(@RequestParam("filter")TxnFilterType txnFilterType,
                                                                    @RequestParam("operator")TxnFilterOperators operators,
                                                                    @RequestParam("values") String value) throws ParseException {
        String[] values = value.split(",");
        List<TxnSearchResponse> listOfTxnSearchResp = txnService.fetchLoggedInUserTxnDetails(txnFilterType,operators,values);
        GenericResponse genericResponse = GenericResponse.builder().
                code(HttpStatus.OK.value()).
                message("Search result is following:").
                statusCode(0).data(listOfTxnSearchResp)
                .build();
        return genericResponse;
    }
}
