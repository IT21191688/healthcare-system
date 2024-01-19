package com.cus.healthcare.system.exception;

public class EntryNotFoundException extends RuntimeException{

    public EntryNotFoundException(String message) {
        super(message);
    }
}
