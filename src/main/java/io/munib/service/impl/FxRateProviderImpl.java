package io.munib.service.impl;

import io.munib.common.ResponseCode;
import io.munib.config.FxRates;
import io.munib.domain.enums.Currency;
import io.munib.error.APIException;
import io.munib.service.FxRateProvider;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class FxRateProviderImpl implements FxRateProvider {
    private final Map<String, BigDecimal> rates;

    public FxRateProviderImpl(FxRates fxRates) {
        this.rates = Map.copyOf(fxRates.fx());
    }

    @Override
    public BigDecimal rate(Currency from, Currency to) {
        if (from == to) return BigDecimal.ONE;

        String key = from.name() + "_" + to.name();
        BigDecimal rate = rates.get(key);

        if (rate == null || rate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new APIException(
                    ResponseCode.FX_RATE_NOT_FOUND,
                    "FX rate not configured for %s -> %s".formatted(from, to)
            );
        }

        return rate;
    }
}