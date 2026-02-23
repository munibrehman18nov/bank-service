package io.munib.service;

import io.munib.domain.enums.Currency;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface FxRateProvider {
    BigDecimal rate(Currency from, Currency to);
}