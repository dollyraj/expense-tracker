package org.gfg.expenseTracker.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UpdateUserRequest {
    private String name;
    private String contact;
}
