package com.example.balance.exception;

/**
 * Thrown when a money amount or currency is invalid.
 */
public class InvalidAmountException extends RuntimeException {

    /**
     * Creates an exception with a message.
     *
     * @param message detail message
     */
    public InvalidAmountException(String message) {
        super(message);
    }
}
