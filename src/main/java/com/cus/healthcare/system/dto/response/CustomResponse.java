package com.cus.healthcare.system.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomResponse {
    private Object data;
    private boolean isSuccessful;
    private int statusCode;
    private String message;


}
