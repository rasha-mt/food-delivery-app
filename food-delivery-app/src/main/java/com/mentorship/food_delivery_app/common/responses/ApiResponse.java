package com.mentorship.food_delivery_app.common.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private Integer status;
    private T data;
    private String message;

}
