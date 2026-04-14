package com.example.balance.service;

import com.example.balance.domain.Account;
import com.example.balance.domain.Money;
import com.example.balance.domain.Transaction;
import com.example.balance.domain.TransactionType;
import com.example.balance.exception.AccountNotFoundException;
import com.example.balance.pipeline.TransactionFilter;
import com.example.balance.repository.GenericRepository;
import com.example.balance.strategy.OverdraftPolicy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Service layer for account and balance operations.
 */
@Service
public class AccountService {

    private final GenericRepository<Account, String> accountRepository;
    private final OverdraftPolicy overdraftPolicy;

    /**
     * Creates service with required dependencies.
     *
     * @param accountRepository account repository
     * @param overdraftPolicy overdraft policy strategy
     */
    public AccountService(GenericRepository<Account, String> accountRepository, OverdraftPolicy overdraftPolicy) {
        this.accountRepository = accountRepository;
        this.overdraftPolicy = overdraftPolicy;
    }

    /**
     * Creates a new account.
     *
     * @param accountId account id
     * @param owner account owner
     * @param initialBalance initial balance
     * @return created account
     */
    public Account createAccount(String accountId, String owner, Money initialBalance) {
        Account account = new Account(accountId, owner, initialBalance, List.of(), overdraftPolicy);
        accountRepository.save(account);
        return account;
    }

    /**
     * Credits account balance.
     *
     * @param accountId account id
     * @param amount amount to credit
     * @param description business description
     * @return updated account
     */
    public Account credit(String accountId, Money amount, String description) {
        Account account = getAccount(accountId);
        Account updated = account.credit(amount, description);
        accountRepository.save(updated);
        return updated;
    }

    /**
     * Debits account balance.
     *
     * @param accountId account id
     * @param amount amount to debit
     * @param description business description
     * @return updated account
     */
    public Account debit(String accountId, Money amount, String description) {
        Account account = getAccount(accountId);
        Account updated = account.debit(amount, description);
        accountRepository.save(updated);
        return updated;
    }

    /**
     * Returns current account balance.
     *
     * @param accountId account id
     * @return account balance
     */
    public Money getBalance(String accountId) {
        return getAccount(accountId).balance();
    }

    /**
     * Returns transaction history for account.
     *
     * @param accountId account id
     * @return transaction list
     */
    public List<Transaction> getTransactionHistory(String accountId) {
        return getAccount(accountId).transactions();
    }

    /**
     * Computes account summary using stream-based transaction analysis.
     *
     * @param accountId account id
     * @return account summary
     */
    public AccountSummary getSummary(String accountId) {
        Account account = getAccount(accountId);
        List<Transaction> transactions = account.transactions();

        Money totalCredits = TransactionFilter.sumAmount(
                TransactionFilter.filterByType(transactions, TransactionType.CREDIT));
        Money totalDebits = TransactionFilter.sumAmount(
                TransactionFilter.filterByType(transactions, TransactionType.DEBIT));

        return new AccountSummary(
                account.accountId(),
                account.owner(),
                account.balance(),
                totalCredits,
                totalDebits,
                transactions.size()
        );
    }

    private Account getAccount(String accountId) {
        Objects.requireNonNull(accountId, "accountId must not be null");
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountId));
    }
}

