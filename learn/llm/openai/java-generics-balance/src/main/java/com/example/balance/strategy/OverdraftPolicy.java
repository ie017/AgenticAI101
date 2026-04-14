package com.example.balance.strategy;

import com.example.balance.domain.Money;

/**
 * Strategy for validating whether a debit is allowed.
 */
@FunctionalInterface
public interface OverdraftPolicy {

    /**
     * Evaluates if debit amount can be applied.
     *
     * @param currentBalance account current balance
     * @param amount debit amount
     * @return true when debit is allowed
     */
    boolean isAllowed(Money currentBalance, Money amount);
}

