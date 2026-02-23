package io.munib.dto;


import io.munib.domain.enums.Currency;

import java.math.BigDecimal;
import java.util.Map;

public record BalanceResponse(Long accountId, Map<Currency, BigDecimal> balances) {
}