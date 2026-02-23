package io.munib.service.impl;

import io.munib.common.ResponseCode;
import io.munib.domain.entity.Account;
import io.munib.domain.entity.AccountBalance;
import io.munib.domain.enums.Currency;
import io.munib.error.APIException;
import io.munib.repository.AccountRepository;
import io.munib.service.ExchangeService;
import io.munib.service.FxRateProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {
    private final AccountRepository accountRepository;
    private final FxRateProvider fxRateProvider;

    @Transactional
    @Override
    public void exchange(Long accountId, Currency from, Currency to, long requestedAmount) {
        if (requestedAmount <= 0) {
            throw new APIException(ResponseCode.INVALID_REQUEST, "Amount must be > 0");
        }
        if (from == to) {
            throw new APIException(ResponseCode.INVALID_REQUEST, "from and to currency must differ");
        }
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new APIException(ResponseCode.RESOURCE_NOT_FOUND, "Account not found: " + accountId));

        AccountBalance fromBal = account.getOrCreateBalance(from);
        // if from currency account balance is not sufficient
        if (fromBal.getAmountMinor() < requestedAmount) {
            throw new APIException(ResponseCode.INSUFFICIENT_FUNDS, "Insufficient funds in " + from);
        }

        BigDecimal rate = fxRateProvider.rate(from, to);
        long targetAmountToAddAsPerFxRate = getTargetAmountToAddAsPerFxRate(rate, requestedAmount);
        subtractAmountFromAccountBalance(fromBal, requestedAmount);

        AccountBalance toBal = account.getOrCreateBalance(to);
        addAmountToAccountBalance(toBal, targetAmountToAddAsPerFxRate);

        accountRepository.save(account);
    }

    private void addAmountToAccountBalance(AccountBalance toBal, long targetAmountToAddAsPerFxRate) {
        toBal.setAmountMinor(Math.addExact(toBal.getAmountMinor(), targetAmountToAddAsPerFxRate));
    }

    private void subtractAmountFromAccountBalance(AccountBalance fromBal, long requestedAmount) {
        fromBal.setAmountMinor(Math.subtractExact(fromBal.getAmountMinor(), requestedAmount));
    }

    private long getTargetAmountToAddAsPerFxRate(BigDecimal rate, long amountMinor) {
        BigDecimal target = BigDecimal.valueOf(amountMinor).multiply(rate);
        return target.setScale(0, RoundingMode.HALF_UP).longValueExact();
    }
}