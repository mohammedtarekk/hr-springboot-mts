package com.example.demo.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String empEmail) {
        super("Employee with email: " + empEmail + " already exists.");
    }
}
