package com.cus.healthcare.system.dto.request;


import lombok.*;


/**
 * should use this annotations
 */
 //@AllArgsConstructor
 // @NoArgsConstructor
 // @ToString
 //We can use for this three in one @Data

@Getter
@Setter
@Data
public class RequestDoctorDto {

    private String name;
    private String address;

    private String contact;

    private double salary;




}
