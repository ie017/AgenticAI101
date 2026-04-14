package com.example.balance.strategy;

import com.example.balance.domain.Money;

/**
 * Overdraft strategy that allows overdraft up to a configured limit.
 */
public class LimitedOverdraftPolicy implements OverdraftPolicy {

    private final Money limit;

    /**
     * Creates policy with a maximum allowed overdraft limit.
     *
     * @param limit maximum overdraft amount
     */
    public LimitedOverdraftPolicy(Money limit) {
        this.limit = limit;
    }

    /**
     * Allows debit when resulting overdraft does not exceed configured limit.
     *
     * @param currentBalance account current balance
     * @param amount debit amount
     * @return true when debit can be executed
     */
    @Override
    public boolean isAllowed(Money currentBalance, Money amount) {
        Money maxAllowedSpend = currentBalance.add(limit);
        return maxAllowedSpend.isGreaterThanOrEqualTo(amount);
    }
}

