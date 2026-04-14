package com.example.balance.config;

import com.example.balance.domain.Account;
import com.example.balance.domain.Money;
import com.example.balance.repository.GenericRepository;
import com.example.balance.repository.InMemoryAccountRepository;
import com.example.balance.strategy.LimitedOverdraftPolicy;
import com.example.balance.strategy.OverdraftPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Spring configuration for balance management beans.
 */
@Configuration
public class BalanceConfig {

    /**
     * Creates overdraft policy with a default limit of 500 USD.
     *
     * @return configured overdraft policy
     */
    @Bean
    public OverdraftPolicy overdraftPolicy() {
        return new LimitedOverdraftPolicy(Money.of(new BigDecimal("500"), Currency.getInstance("USD")));
    }

    /**
     * Exposes generic account repository bean.
     *
     * @param repository in-memory repository implementation
     * @return generic account repository
     */
    @Bean
    @Primary
    public GenericRepository<Account, String> accountRepository(InMemoryAccountRepository repository) {
        return repository;
    }
}
