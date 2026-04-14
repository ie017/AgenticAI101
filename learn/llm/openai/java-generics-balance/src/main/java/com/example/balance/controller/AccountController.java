package com.example.balance.controller;

import com.example.balance.controller.dto.CreateAccountRequest;
import com.example.balance.controller.dto.TransactionRequest;
import com.example.balance.domain.Account;
import com.example.balance.domain.Money;
import com.example.balance.domain.Transaction;
import com.example.balance.service.AccountService;
import com.example.balance.service.AccountSummary;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Currency;
import java.util.List;

/**
 * REST controller for account balance management endpoints.
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    /**
     * Creates controller with account service dependency.
     *
     * @param accountService account service
     */
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Creates an account.
     *
     * @param request payload
     * @return created account
     */
    @PostMapping
    public Account createAccount(@Valid @RequestBody CreateAccountRequest request) {
        Money initialBalance = Money.of(request.initialAmount(), Currency.getInstance(request.currencyCode()));
        return accountService.createAccount(request.accountId(), request.owner(), initialBalance);
    }

    /**
     * Credits an account.
     *
     * @param id account id
     * @param request payload
     * @return updated account
     */
    @PostMapping("/{id}/credit")
    public Account credit(@PathVariable("id") String id, @Valid @RequestBody TransactionRequest request) {
        Money amount = Money.of(request.amount(), Currency.getInstance(request.currencyCode()));
        return accountService.credit(id, amount, request.description());
    }

    /**
     * Debits an account.
     *
     * @param id account id
     * @param request payload
     * @return updated account
     */
    @PostMapping("/{id}/debit")
    public Account debit(@PathVariable("id") String id, @Valid @RequestBody TransactionRequest request) {
        Money amount = Money.of(request.amount(), Currency.getInstance(request.currencyCode()));
        return accountService.debit(id, amount, request.description());
    }

    /**
     * Returns current balance for account.
     *
     * @param id account id
     * @return money balance
     */
    @GetMapping("/{id}/balance")
    public Money getBalance(@PathVariable("id") String id) {
        return accountService.getBalance(id);
    }

    /**
     * Returns transaction history for account.
     *
     * @param id account id
     * @return transaction list
     */
    @GetMapping("/{id}/transactions")
    public List<Transaction> getTransactions(@PathVariable("id") String id) {
        return accountService.getTransactionHistory(id);
    }

    /**
     * Returns account summary.
     *
     * @param id account id
     * @return account summary
     */
    @GetMapping("/{id}/summary")
    public AccountSummary getSummary(@PathVariable("id") String id) {
        return accountService.getSummary(id);
    }
}

