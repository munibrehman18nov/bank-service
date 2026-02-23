package io.munib.service;

import io.munib.domain.enums.Currency;
import jakarta.transaction.Transactional;

public interface ExchangeService {

    @Transactional
    void exchange(Long accountId, Currency from, Currency to, long amountMinor);
}
