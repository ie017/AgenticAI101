package com.example.balance.domain;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Immutable transaction event.
 *
 * @param id transaction id
 * @param type transaction type
 * @param amount transaction amount
 * @param timestamp transaction timestamp
 * @param description optional business description
 */
public record Transaction(UUID id, TransactionType type, Money amount, LocalDateTime timestamp, String description) {

    /**
     * Factory for credit transaction.
     *
     * @param amount transaction amount
     * @param description transaction description
     * @return credit transaction
     */
    public static Transaction credit(Money amount, String description) {
        return new Transaction(UUID.randomUUID(), TransactionType.CREDIT, amount, LocalDateTime.now(), description);
    }

    /**
     * Factory for debit transaction.
     *
     * @param amount transaction amount
     * @param description transaction description
     * @return debit transaction
     */
    public static Transaction debit(Money amount, String description) {
        return new Transaction(UUID.randomUUID(), TransactionType.DEBIT, amount, LocalDateTime.now(), description);
    }
}

