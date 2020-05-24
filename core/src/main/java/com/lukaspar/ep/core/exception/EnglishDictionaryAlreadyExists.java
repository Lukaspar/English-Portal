package com.lukaspar.ep.core.exception;

public class EnglishDictionaryAlreadyExists extends RuntimeException {

    public EnglishDictionaryAlreadyExists(String englishWord){
        super("English word: " + englishWord + " already exists in english dictionary.");
    }
}
