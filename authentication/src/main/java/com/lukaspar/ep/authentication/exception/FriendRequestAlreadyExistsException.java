package com.lukaspar.ep.authentication.exception;

public class FriendRequestAlreadyExistsException extends RuntimeException {

    public FriendRequestAlreadyExistsException(Long ownerId, Long friendId) {
        super("Friend request with owner ID: " + ownerId + " and friend ID: " + friendId + " already exists.");
    }

}
