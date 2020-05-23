package com.lukaspar.ep.authentication.exception;

public class MessageReceiverIdenticalLikeSenderException extends RuntimeException {

    public MessageReceiverIdenticalLikeSenderException(){
        super("Message receiver must be different than sender.");
    }
}
