package com.example.balance;

import com.example.balance.domain.Money;
import com.example.balance.repository.InMemoryAccountRepository;
import com.example.balance.service.AccountService;
import com.example.balance.service.AccountSummary;
import com.example.balance.strategy.NoOverdraftPolicy;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountServiceTest {

    @Test
    void shouldCreateCreditDebitAndSummarizeAccount() {
        AccountService service = new AccountService(new InMemoryAccountRepository(), new NoOverdraftPolicy());
        Currency usd = Currency.getInstance("USD");

        service.createAccount("A-1", "Alice", Money.of(new BigDecimal("100.00"), usd));
        service.credit("A-1", Money.of(new BigDecimal("50.00"), usd), "Bonus");
        service.debit("A-1", Money.of(new BigDecimal("20.00"), usd), "Lunch");

        assertEquals(new BigDecimal("130.00"), service.getBalance("A-1").amount());

        AccountSummary summary = service.getSummary("A-1");
        assertEquals(new BigDecimal("50.00"), summary.totalCredits().amount());
        assertEquals(new BigDecimal("20.00"), summary.totalDebits().amount());
        assertEquals(2L, summary.transactionCount());
    }
}

