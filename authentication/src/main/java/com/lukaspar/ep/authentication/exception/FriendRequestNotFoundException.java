package com.lukaspar.ep.authentication.exception;

public class FriendRequestNotFoundException extends RuntimeException {

    public FriendRequestNotFoundException(Long ownerId, Long friendId) {
        super("Friend request with owner ID: " + ownerId + " and friend ID: " + friendId + " not found.");
    }

}
