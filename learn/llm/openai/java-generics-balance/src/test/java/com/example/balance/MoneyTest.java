package com.example.balance;

import com.example.balance.domain.Money;
import com.example.balance.exception.InvalidAmountException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoneyTest {

    @Test
    void shouldAddAndSubtractMoneyInSameCurrency() {
        Currency usd = Currency.getInstance("USD");
        Money current = Money.of(new BigDecimal("100.00"), usd);
        Money delta = Money.of(new BigDecimal("25.00"), usd);

        assertEquals(new BigDecimal("125.00"), current.add(delta).amount());
        assertEquals(new BigDecimal("75.00"), current.subtract(delta).amount());
    }

    @Test
    void shouldRejectNegativeAmount() {
        Currency usd = Currency.getInstance("USD");

        assertThrows(InvalidAmountException.class,
                () -> Money.of(new BigDecimal("-1.00"), usd));
    }
}

