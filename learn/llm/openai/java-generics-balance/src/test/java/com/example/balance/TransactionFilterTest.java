package com.example.balance;

import com.example.balance.domain.Money;
import com.example.balance.domain.Transaction;
import com.example.balance.domain.TransactionType;
import com.example.balance.pipeline.TransactionFilter;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionFilterTest {

    @Test
    void shouldFilterGroupAndTopTransactions() {
        Currency usd = Currency.getInstance("USD");
        Transaction credit1 = new Transaction(
                UUID.randomUUID(),
                TransactionType.CREDIT,
                Money.of(new BigDecimal("10.00"), usd),
                LocalDateTime.now().minusDays(2),
                "c1"
        );
        Transaction debit = new Transaction(
                UUID.randomUUID(),
                TransactionType.DEBIT,
                Money.of(new BigDecimal("4.00"), usd),
                LocalDateTime.now().minusDays(1),
                "d1"
        );
        Transaction credit2 = new Transaction(
                UUID.randomUUID(),
                TransactionType.CREDIT,
                Money.of(new BigDecimal("20.00"), usd),
                LocalDateTime.now(),
                "c2"
        );

        List<Transaction> transactions = List.of(credit1, debit, credit2);

        assertEquals(2, TransactionFilter.filterByType(transactions, TransactionType.CREDIT).size());
        assertEquals(3, TransactionFilter.filterByDateRange(transactions,
                LocalDateTime.now().minusDays(3), LocalDateTime.now().plusDays(1)).size());
        assertEquals(new BigDecimal("34.00"), TransactionFilter.sumAmount(transactions).amount());
        assertEquals(2, TransactionFilter.groupByType(transactions).size());
        assertEquals(new BigDecimal("20.00"), TransactionFilter.topNByAmount(transactions, 1).get(0).amount().amount());
    }
}
