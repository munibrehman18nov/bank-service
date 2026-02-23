package io.munib.service.impl;

import io.munib.domain.enums.Currency;
import io.munib.service.CurrencyExchangeService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private static final Map<Currency, BigDecimal> EUR_RATES = Map.of(
            Currency.EUR, BigDecimal.ONE,
            Currency.USD, new BigDecimal("1.08"),
            Currency.GBP, new BigDecimal("0.86"),
            Currency.SEK, new BigDecimal("11.20")
    );

    @Override
    public BigDecimal exchange(BigDecimal amount, Currency from, Currency to) {
        BigDecimal inEur = amount.divide(EUR_RATES.get(from), 6, RoundingMode.HALF_UP);
        return inEur.multiply(EUR_RATES.get(to));
    }
}
