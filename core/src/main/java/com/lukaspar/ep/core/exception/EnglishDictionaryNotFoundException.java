package com.lukaspar.ep.core.exception;

public class EnglishDictionaryNotFoundException extends RuntimeException {

    public EnglishDictionaryNotFoundException(String englishWord){
        super("Not found english dictionary for english word: " + englishWord);
    }
}
