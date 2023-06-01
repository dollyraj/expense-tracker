package org.gfg.expenseTracker.model;

import lombok.Getter;

@Getter
public enum UserType {
    ADMIN(0, "admin"),
    //SUBSCRIBER(1,"subscriber"),
    //NON_SUBSCRIBER(2,"non-subscriber"),
    //GUEST(3,"guest");
    USER(1,"user");

    Integer id;
    String userType;

    UserType(Integer id, String userType){
        this.id =id ;
        this.userType = userType;
    }


}
