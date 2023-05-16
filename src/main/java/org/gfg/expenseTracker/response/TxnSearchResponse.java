package org.gfg.expenseTracker.response;

import lombok.*;
import org.gfg.expenseTracker.model.User;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TxnSearchResponse {

    private User user;
    private Double expenditureAmount;
    private String expenseType;
    private String expenseDate;
}
