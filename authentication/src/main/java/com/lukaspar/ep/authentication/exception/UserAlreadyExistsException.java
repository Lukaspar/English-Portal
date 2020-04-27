package com.lukaspar.ep.authentication.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String username) {
        super("User: " + username + " already exists.");
    }
}
