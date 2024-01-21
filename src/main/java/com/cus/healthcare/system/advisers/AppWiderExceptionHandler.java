package com.cus.healthcare.system.advisers;

import com.cus.healthcare.system.exception.EntryNotFoundException;
import com.cus.healthcare.system.utill.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppWiderExceptionHandler {

    @ExceptionHandler(EntryNotFoundException.class)
    public ResponseEntity<StandardResponse>handleEntryNotFoundException(EntryNotFoundException e){

        return new ResponseEntity<>(
                new StandardResponse(404,e.getMessage(),""),
                HttpStatus.NOT_FOUND
        );

    }
}
