package com.example.balance.controller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * Request payload for account creation.
 *
 * @param accountId account identifier
 * @param owner owner name
 * @param initialAmount initial account amount
 * @param currencyCode ISO currency code
 */
public record CreateAccountRequest(
        @NotBlank String accountId,
        @NotBlank String owner,
        @NotNull @DecimalMin("0.00") BigDecimal initialAmount,
        @NotBlank String currencyCode
) {
}

