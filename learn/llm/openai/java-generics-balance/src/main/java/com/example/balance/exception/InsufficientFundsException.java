package com.example.balance.exception;

/**
 * Thrown when a debit is blocked by overdraft policy.
 */
public class InsufficientFundsException extends RuntimeException {

    /**
     * Creates an exception with a message.
     *
     * @param message detail message
     */
    public InsufficientFundsException(String message) {
        super(message);
    }
}
