package com.example.balance.decorator;

import com.example.balance.domain.Account;
import com.example.balance.domain.Money;
import com.example.balance.domain.Transaction;
import com.example.balance.service.AccountService;
import com.example.balance.service.AccountSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Decorator for {@link AccountService} that logs operations before delegation.
 */
public class LoggingAccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAccountService.class);

    private final AccountService delegate;

    /**
     * Creates a logging decorator around an account service.
     *
     * @param delegate wrapped account service
     */
    public LoggingAccountService(AccountService delegate) {
        this.delegate = delegate;
    }

    /**
     * Delegates account creation with logging.
     *
     * @param accountId account id
     * @param owner owner
     * @param initialBalance initial balance
     * @return created account
     */
    public Account createAccount(String accountId, String owner, Money initialBalance) {
        LOGGER.info("Creating account {} for owner {}", accountId, owner);
        return delegate.createAccount(accountId, owner, initialBalance);
    }

    /**
     * Delegates account credit with logging.
     *
     * @param accountId account id
     * @param amount credit amount
     * @param description description
     * @return updated account
     */
    public Account credit(String accountId, Money amount, String description) {
        LOGGER.info("Crediting account {} with {} ({})", accountId, amount, description);
        return delegate.credit(accountId, amount, description);
    }

    /**
     * Delegates account debit with logging.
     *
     * @param accountId account id
     * @param amount debit amount
     * @param description description
     * @return updated account
     */
    public Account debit(String accountId, Money amount, String description) {
        LOGGER.info("Debiting account {} with {} ({})", accountId, amount, description);
        return delegate.debit(accountId, amount, description);
    }

    /**
     * Delegates balance retrieval with logging.
     *
     * @param accountId account id
     * @return balance
     */
    public Money getBalance(String accountId) {
        LOGGER.info("Fetching balance for account {}", accountId);
        return delegate.getBalance(accountId);
    }

    /**
     * Delegates transaction history retrieval with logging.
     *
     * @param accountId account id
     * @return transactions
     */
    public List<Transaction> getTransactionHistory(String accountId) {
        LOGGER.info("Fetching transactions for account {}", accountId);
        return delegate.getTransactionHistory(accountId);
    }

    /**
     * Delegates summary retrieval with logging.
     *
     * @param accountId account id
     * @return account summary
     */
    public AccountSummary getSummary(String accountId) {
        LOGGER.info("Fetching summary for account {}", accountId);
        return delegate.getSummary(accountId);
    }
}

