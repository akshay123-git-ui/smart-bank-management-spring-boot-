package com.smartbank.util;

/**
 * A single unchecked exception type covers most learning-project needs:
 * insufficient balance, duplicate email, account not found, etc.
 * As a follow-up exercise, try splitting this into more specific subclasses
 * (InsufficientBalanceException, DuplicateEmailException, ...) and see how
 * that changes your @ExceptionHandler logic in the controllers.
 */
public class BankException extends RuntimeException {
    public BankException(String message) {
        super(message);
    }
}
