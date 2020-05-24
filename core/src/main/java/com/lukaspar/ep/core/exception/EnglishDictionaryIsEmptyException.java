package com.lukaspar.ep.core.exception;

public class EnglishDictionaryIsEmptyException extends RuntimeException {

    public EnglishDictionaryIsEmptyException(){
        super("Not found any word in english dictionary.");
    }
}
