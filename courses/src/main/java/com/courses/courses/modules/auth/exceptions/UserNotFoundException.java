package com.courses.courses.modules.auth.exceptions;


public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Invalid Credentials!");
    }
}


