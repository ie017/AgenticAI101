package com.example.balance.domain;

import com.example.balance.exception.InvalidAmountException;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

/**
 * Immutable value object for money.
 *
 * @param amount monetary amount
 * @param currency currency of amount
 */
public record Money(BigDecimal amount, Currency currency) {

    /**
     * Creates a money instance after validation.
     *
     * @param amount monetary amount
     * @param currency monetary currency
     * @return validated money instance
     */
    public static Money of(BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }

    /**
     * Canonical constructor with validation.
     *
     * @param amount monetary amount
     * @param currency monetary currency
     */
    public Money {
        if (amount == null) {
            throw new InvalidAmountException("Amount must not be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAmountException("Amount must not be negative");
        }
        if (currency == null) {
            throw new InvalidAmountException("Currency must not be null");
        }
    }

    /**
     * Adds two money values in the same currency.
     *
     * @param other money to add
     * @return sum
     */
    public Money add(Money other) {
        requireSameCurrency(other);
        return Money.of(amount.add(other.amount), currency);
    }

    /**
     * Subtracts money values in the same currency.
     *
     * @param other money to subtract
     * @return difference
     */
    public Money subtract(Money other) {
        requireSameCurrency(other);
        return Money.of(amount.subtract(other.amount), currency);
    }

    /**
     * Compares amount with another money value in same currency.
     *
     * @param other money to compare
     * @return true when this amount is greater or equal
     */
    public boolean isGreaterThanOrEqualTo(Money other) {
        requireSameCurrency(other);
        return amount.compareTo(other.amount) >= 0;
    }

    private void requireSameCurrency(Money other) {
        Objects.requireNonNull(other, "Money must not be null");
        if (!currency.equals(other.currency)) {
            throw new InvalidAmountException("Currency mismatch: " + currency + " vs " + other.currency);
        }
    }

    @Override
    public String toString() {
        return currency.getCurrencyCode() + " " + amount;
    }
}

