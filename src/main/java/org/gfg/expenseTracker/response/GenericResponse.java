package org.gfg.expenseTracker.response;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenericResponse<T> {
    private Integer statusCode; // 0-> successful 1-> failure
    private String message;
    private Integer code;
    private T data; // data will vary according to response from different apis?


}
