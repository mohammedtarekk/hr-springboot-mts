package com.example.demo.exceptions;

public class MissingDataException extends RuntimeException {
    public MissingDataException(String s) {
        super(s);
    }
}
