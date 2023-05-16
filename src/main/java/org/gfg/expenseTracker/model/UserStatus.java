package org.gfg.expenseTracker.model;

public enum UserStatus {
    ACTIVE(1),
    INACTIVE(0),
    BLOCKED(2);

    private Integer userVal;

    UserStatus(Integer userVal){this.userVal = userVal;}
}
