package com.lukaspar.ep.authentication.exception;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String name) {
        super("Role: " + name + " not found.");
    }
}
