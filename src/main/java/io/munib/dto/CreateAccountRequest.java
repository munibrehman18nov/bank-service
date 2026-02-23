package io.munib.dto;

import jakarta.validation.constraints.NotNull;

public record CreateAccountRequest(@NotNull Boolean initializeAllCurrencies) {
}