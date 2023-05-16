package org.gfg.expenseTracker.request;

import lombok.*;
import org.gfg.expenseTracker.model.ExpenseTypes;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateExpenseTypeRequest {
    @NotBlank(message = "expense type can't be blank")
    String expenseType;

    @NotBlank(message = "user email can't be blank")
    String userEmail;

    public ExpenseTypes toExpenseTypes() {
        return ExpenseTypes.builder().
                expenseType(this.expenseType).
                createdBy(this.userEmail).
                build();
    }
}
