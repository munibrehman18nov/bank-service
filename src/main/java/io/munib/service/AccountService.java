package io.munib.service;

import io.munib.domain.enums.Currency;
import io.munib.dto.BalanceResponse;
import jakarta.transaction.Transactional;

public interface AccountService {

    @Transactional
    Long createAccount(boolean initializeAllCurrencies);

    @Transactional
    BalanceResponse getBalance(Long accountId);

    @Transactional
    BalanceResponse credit(Long accountId, Currency currency, long amountMinor);

    @Transactional
    BalanceResponse debit(Long accountId, Currency currency, long amountMinor);
}
