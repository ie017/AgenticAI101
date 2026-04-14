package com.example.balance.exception;

/**
 * Thrown when an account cannot be found.
 */
public class AccountNotFoundException extends RuntimeException {

    /**
     * Creates an exception with a message.
     *
     * @param message detail message
     */
    public AccountNotFoundException(String message) {
        super(message);
    }
}
