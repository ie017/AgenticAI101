package com.example.balance.strategy;

import com.example.balance.domain.Money;

/**
 * Overdraft strategy that disallows spending over current balance.
 */
public class NoOverdraftPolicy implements OverdraftPolicy {

    /**
     * Allows debit only when amount is not greater than balance.
     *
     * @param currentBalance account current balance
     * @param amount debit amount
     * @return true when debit can be executed
     */
    @Override
    public boolean isAllowed(Money currentBalance, Money amount) {
        return currentBalance.isGreaterThanOrEqualTo(amount);
    }
}

