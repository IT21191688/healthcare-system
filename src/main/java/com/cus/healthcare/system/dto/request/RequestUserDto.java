package com.cus.healthcare.system.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUserDto {
    private String id;
    private String fullName;
    private String email;
    private String password;
    private long roleId;
}
