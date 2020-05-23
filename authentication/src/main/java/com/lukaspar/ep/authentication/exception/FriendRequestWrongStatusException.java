package com.lukaspar.ep.authentication.exception;

import com.lukaspar.ep.authentication.util.FriendshipStatus;

public class FriendRequestWrongStatusException extends RuntimeException {

    public FriendRequestWrongStatusException(FriendshipStatus status){
        super("Can not accept or reject friend request. Request status must be REQUESTED but is: " + status.name());
    }
}
