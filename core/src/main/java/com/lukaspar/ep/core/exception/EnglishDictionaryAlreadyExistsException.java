package com.lukaspar.ep.core.exception;

public class EnglishDictionaryAlreadyExistsException extends RuntimeException {

    public EnglishDictionaryAlreadyExistsException(String englishWord){
        super("English word: " + englishWord + " already exists in english dictionary.");
    }
}
