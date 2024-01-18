package com.cus.healthcare.system.dto.response;


import lombok.Data;

@Data
public class ResponseDoctorDto {

    private long id;
    private String name;
    private String address;
    private String contact;
    private double salary;
}
