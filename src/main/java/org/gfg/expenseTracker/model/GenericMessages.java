package org.gfg.expenseTracker.model;

import lombok.Getter;

@Getter
public enum GenericMessages {
    FAILURE("failure"),
    SUCCESS("Success"),
    SIGNUP_SUCCESS("Signup Successful."),
    SIGNUP_FAILURE("Signup Failure."),
    ENTRY_ALREADY_PRESENT("Entry Already present"),
    EXPENSE_TYPE_ADDITION_SUCCESS("expense type addition success"),
    EXPENSE_TYPE_ADDITION_FAILURE("expense type addition failure"),
    TXN_ADDITION_SUCCESS("Txn addition success"),
    TXN_ADDITION_FAILURE("Txn addition failure"),
    USER_NOT_PRESENT("user not present"),
    PROFILE_UPDATE_SUCCESS("profile successfully updated"),
    PROFILE_UPDATE_FAILURE("profile successfully failed");

    private String message;

    GenericMessages(String message){
        this.message = message;

    }

}
