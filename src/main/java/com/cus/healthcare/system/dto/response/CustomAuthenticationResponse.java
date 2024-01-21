package com.cus.healthcare.system.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomAuthenticationResponse {
    private String token;
    private String message;
    private Object data; // You can include additional data in the response

}
