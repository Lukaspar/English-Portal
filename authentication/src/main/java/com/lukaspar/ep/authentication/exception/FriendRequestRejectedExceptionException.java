package com.lukaspar.ep.authentication.exception;

public class FriendRequestRejectedExceptionException extends RuntimeException {

    public FriendRequestRejectedExceptionException(Long id, Long friendId){
        super("Could not send friend request because user rejected request last time. Owner ID: " + id + ", friend ID: " + friendId);
    }
}
