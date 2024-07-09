package com.example.demo.exceptions;

public class InsufficientVacationBalanceException extends RuntimeException {
    public InsufficientVacationBalanceException(String insufficientVacationBalance) {
        super(insufficientVacationBalance);
    }
}
