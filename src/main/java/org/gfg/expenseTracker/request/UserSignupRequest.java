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
public class UserSignupRequest {
    private String username;
    @NotBlank(message = "user email should not blank.")
    private String userEmail;



    @NotNull(message = "user contact can't be null")
    private  String contact;

    private String password;
    public User toUser() {
        return  User.builder().
                name(this.username).
                email(this.userEmail).
                contact(this.contact).
                build();
    }
}
