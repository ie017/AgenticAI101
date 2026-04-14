package com.example.balance.controller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * Request payload for debit or credit transaction.
 *
 * @param amount amount to apply
 * @param currencyCode ISO currency code
 * @param description business description
 */
public record TransactionRequest(
        @NotNull @DecimalMin("0.00") BigDecimal amount,
        @NotBlank String currencyCode,
        @NotBlank String description
) {
}

