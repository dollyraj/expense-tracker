package org.gfg.expenseTracker.request;

import lombok.*;
import org.gfg.expenseTracker.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CreateUserRequest {
    @NotBlank(message = "user email should not blank.")
    private String email;

    private String name;

    @NotNull(message = "user contact can't be null")
    private  String contact;

    public User toUser() {
        return  User.builder().
                name(this.name).
                email(this.email).
                contact(this.contact).
                build();
    }
}
