package com.example.demo.exceptions;


public class EmployeeNotFoundException extends NotFoundException {
    public EmployeeNotFoundException(Long id) {
        super("Employee with Id: " + id + " is not found.");
    }
}
