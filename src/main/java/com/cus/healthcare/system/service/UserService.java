package com.cus.healthcare.system.service;


import com.cus.healthcare.system.dto.request.RequestUserDto;

public interface UserService {
    public String signup(RequestUserDto userDto);
    public boolean verifyUser(String type, String token);
}
