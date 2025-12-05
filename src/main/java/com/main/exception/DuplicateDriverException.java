package com.main.exception;

public class DuplicateDriverException extends  RuntimeException{
    public DuplicateDriverException(String message){
        super(message);
    }
}
