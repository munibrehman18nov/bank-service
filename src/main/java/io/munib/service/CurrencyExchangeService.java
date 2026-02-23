package io.munib.service;

import io.munib.domain.enums.Currency;

import java.math.BigDecimal;

public interface CurrencyExchangeService {

    BigDecimal exchange(BigDecimal amount, Currency from, Currency to);
}
