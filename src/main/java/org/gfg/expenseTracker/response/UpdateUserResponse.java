package org.gfg.expenseTracker.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserResponse {
    private Integer userId;
    private String name;
    private String Contact;
    private String message;
}
