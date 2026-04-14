package com.example.balance.domain;

import com.example.balance.exception.InsufficientFundsException;
import com.example.balance.exception.InvalidAmountException;
import com.example.balance.strategy.NoOverdraftPolicy;
import com.example.balance.strategy.OverdraftPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Immutable-style account aggregate root with defensive copies.
 */
public final class Account {

    private final String accountId;
    private final String owner;
    private final Money balance;
    private final List<Transaction> transactions;
    private final OverdraftPolicy overdraftPolicy;

    /**
     * Creates account using no-overdraft policy by default.
     *
     * @param accountId account identifier
     * @param owner account owner
     * @param balance initial balance
     */
    public Account(String accountId, String owner, Money balance) {
        this(accountId, owner, balance, List.of(), new NoOverdraftPolicy());
    }

    /**
     * Creates account with provided overdraft policy and transactions.
     *
     * @param accountId account identifier
     * @param owner account owner
     * @param balance current balance
     * @param transactions transaction history
     * @param overdraftPolicy overdraft strategy
     */
    public Account(String accountId, String owner, Money balance, List<Transaction> transactions, OverdraftPolicy overdraftPolicy) {
        this.accountId = Objects.requireNonNull(accountId, "accountId must not be null");
        this.owner = Objects.requireNonNull(owner, "owner must not be null");
        this.balance = Objects.requireNonNull(balance, "balance must not be null");
        this.transactions = List.copyOf(Objects.requireNonNull(transactions, "transactions must not be null"));
        this.overdraftPolicy = Objects.requireNonNull(overdraftPolicy, "overdraftPolicy must not be null");
    }

    /**
     * Returns the account id.
     *
     * @return account id
     */
    public String accountId() {
        return accountId;
    }

    /**
     * Returns account owner.
     *
     * @return owner
     */
    public String owner() {
        return owner;
    }

    /**
     * Returns current balance.
     *
     * @return balance
     */
    public Money balance() {
        return balance;
    }

    /**
     * Returns immutable transaction history.
     *
     * @return transactions
     */
    public List<Transaction> transactions() {
        return List.copyOf(transactions);
    }

    /**
     * Returns a new account instance with credited balance.
     *
     * @param amount amount to credit
     * @param description transaction description
     * @return new account instance
     */
    public Account credit(Money amount, String description) {
        Transaction transaction = Transaction.credit(amount, description);
        List<Transaction> updatedTransactions = new ArrayList<>(transactions);
        updatedTransactions.add(transaction);
        return new Account(accountId, owner, balance.add(amount), updatedTransactions, overdraftPolicy);
    }

    /**
     * Returns a new account instance with debited balance if allowed.
     *
     * @param amount amount to debit
     * @param description transaction description
     * @return new account instance
     */
    public Account debit(Money amount, String description) {
        if (!overdraftPolicy.isAllowed(balance, amount)) {
            throw new InsufficientFundsException("Debit denied by overdraft policy for account: " + accountId);
        }

        final Money updatedBalance;
        try {
            updatedBalance = balance.subtract(amount);
        } catch (InvalidAmountException exception) {
            throw new InsufficientFundsException("Debit denied because resulting balance would be negative for account: " + accountId);
        }

        Transaction transaction = Transaction.debit(amount, description);
        List<Transaction> updatedTransactions = new ArrayList<>(transactions);
        updatedTransactions.add(transaction);
        return new Account(accountId, owner, updatedBalance, updatedTransactions, overdraftPolicy);
    }
}
