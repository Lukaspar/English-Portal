package com.lukaspar.ep.authentication.exception;

public class FriendRequestRejectedException extends RuntimeException {

    public FriendRequestRejectedException(Long id, Long friendId){
        super("Could not send friend request because user rejected request last time. Owner ID: " + id + ", friend ID: " + friendId);
    }
}
