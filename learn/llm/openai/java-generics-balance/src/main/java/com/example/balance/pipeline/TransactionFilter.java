package com.example.balance.pipeline;

import com.example.balance.domain.Money;
import com.example.balance.domain.Transaction;
import com.example.balance.domain.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Stream-based utilities for transaction analysis.
 */
public final class TransactionFilter {

    private TransactionFilter() {
    }

    /**
     * Filters transactions by type.
     *
     * @param transactions source transactions
     * @param type expected type
     * @return filtered transactions
     */
    public static List<Transaction> filterByType(List<Transaction> transactions, TransactionType type) {
        Objects.requireNonNull(transactions, "transactions must not be null");
        Objects.requireNonNull(type, "type must not be null");
        return transactions.stream()
                .filter(transaction -> transaction.type() == type)
                .toList();
    }

    /**
     * Filters transactions by inclusive date range.
     *
     * @param transactions source transactions
     * @param from start timestamp inclusive
     * @param to end timestamp inclusive
     * @return filtered transactions
     */
    public static List<Transaction> filterByDateRange(List<Transaction> transactions, LocalDateTime from, LocalDateTime to) {
        Objects.requireNonNull(transactions, "transactions must not be null");
        Objects.requireNonNull(from, "from must not be null");
        Objects.requireNonNull(to, "to must not be null");
        return transactions.stream()
                .filter(transaction -> !transaction.timestamp().isBefore(from) && !transaction.timestamp().isAfter(to))
                .toList();
    }

    /**
     * Sums transaction amounts.
     *
     * @param transactions source transactions
     * @return total amount money
     */
    public static Money sumAmount(List<Transaction> transactions) {
        Objects.requireNonNull(transactions, "transactions must not be null");
        Currency currency = transactions.stream()
                .map(transaction -> transaction.amount().currency())
                .findFirst()
                .orElse(Currency.getInstance("USD"));

        BigDecimal total = transactions.stream()
                .peek(transaction -> ensureCurrency(transaction, currency))
                .map(Transaction::amount)
                .map(Money::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return Money.of(total, currency);
    }

    /**
     * Groups transactions by type.
     *
     * @param transactions source transactions
     * @return grouped transactions
     */
    public static Map<TransactionType, List<Transaction>> groupByType(List<Transaction> transactions) {
        Objects.requireNonNull(transactions, "transactions must not be null");
        return transactions.stream().collect(Collectors.groupingBy(Transaction::type));
    }

    /**
     * Returns top n transactions by amount descending.
     *
     * @param transactions source transactions
     * @param n max number of transactions
     * @return top n transactions
     */
    public static List<Transaction> topNByAmount(List<Transaction> transactions, int n) {
        Objects.requireNonNull(transactions, "transactions must not be null");
        if (n <= 0) {
            return List.of();
        }
        return transactions.stream()
                .sorted(Comparator.comparing((Transaction transaction) -> transaction.amount().amount()).reversed())
                .limit(n)
                .toList();
    }

    private static void ensureCurrency(Transaction transaction, Currency currency) {
        if (!transaction.amount().currency().equals(currency)) {
            throw new IllegalArgumentException("All transactions must share the same currency");
        }
    }
}
