package com.example.balance.service;

import com.example.balance.domain.Money;

/**
 * Immutable account summary projection.
 *
 * @param accountId account identifier
 * @param owner account owner
 * @param currentBalance current account balance
 * @param totalCredits total credit amount
 * @param totalDebits total debit amount
 * @param transactionCount number of transactions
 */
public record AccountSummary(
        String accountId,
        String owner,
        Money currentBalance,
        Money totalCredits,
        Money totalDebits,
        long transactionCount
) {
}

