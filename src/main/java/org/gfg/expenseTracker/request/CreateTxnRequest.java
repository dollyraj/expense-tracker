package org.gfg.expenseTracker.request;

import lombok.*;
import org.gfg.expenseTracker.model.ExpenseTypes;
import org.gfg.expenseTracker.model.TxnDetails;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTxnRequest {
    @NotBlank(message = "user email can't be blank")
    private String userEmail;

    private String expenseType;
    private Double expenditureCost;
    private LocalDate expenseDate;

    private String expenseNote;


    public TxnDetails toTxnDetails(CreateTxnRequest createTxnRequest) {

        return TxnDetails.builder().
                expenditureAmount(this.expenditureCost).
                expenseDate(expenseDate).
                expenseNote(this.expenseNote).build();

    }


}
