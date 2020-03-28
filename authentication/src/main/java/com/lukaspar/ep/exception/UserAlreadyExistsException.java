package com.lukaspar.ep.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String username) {
        super("User: " + username + " already exists.");
    }
}
