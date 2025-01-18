package com.exercise.transaction_service.exception;

public class UnavailableBalanceException extends RuntimeException {
    public UnavailableBalanceException(String message) {
        super(message);
    }
}

