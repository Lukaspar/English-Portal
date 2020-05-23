package com.lukaspar.ep.authentication.exception;

public class MessageReceiverMustBeSenderFriendException extends RuntimeException {

    public MessageReceiverMustBeSenderFriendException(){
        super("Message receiver must be a friend of sender.");
    }
}
