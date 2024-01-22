package com.cus.healthcare.system.api;

import com.cus.healthcare.system.dto.request.RequestUserDto;
import com.cus.healthcare.system.dto.response.CustomResponse;
import com.cus.healthcare.system.service.UserService;
import com.cus.healthcare.system.utill.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/visitor/signup")
    public ResponseEntity<CustomResponse> createDoctor(@RequestBody RequestUserDto userDto){
        String userId=userService.signup(userDto);

        userDto.setId(userId);

        CustomResponse customResponse = new CustomResponse();
        customResponse.setData(userDto);
        customResponse.setSuccessful(true); // Set to true if the operation was successful
        customResponse.setStatusCode(HttpStatus.OK.value()); // Use appropriate HTTP status code
        customResponse.setMessage("User Save Success!");

        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/verify", params = {"type"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DOCTOR')")
    public ResponseEntity<StandardResponse> verifyUser(
            @RequestParam String type,
            @RequestHeader("Authorization") String token
    ){

        return new ResponseEntity<>(
                new StandardResponse(200,"user state!",
                        userService.verifyUser(type,token)),
                HttpStatus.OK
        );
    }

}