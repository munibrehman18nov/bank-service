package io.munib.dto;

import io.munib.domain.enums.Currency;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ExchangeRequest(@NotNull Currency from, @NotNull Currency to, @Min(1) BigDecimal amount) {
}