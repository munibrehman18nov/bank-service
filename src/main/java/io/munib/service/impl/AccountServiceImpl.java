package io.munib.service.impl;

import io.munib.common.ResponseCode;
import io.munib.domain.entity.Account;
import io.munib.domain.entity.AccountBalance;
import io.munib.domain.enums.Currency;
import io.munib.dto.BalanceResponse;
import io.munib.error.APIException;
import io.munib.repository.AccountRepository;
import io.munib.service.AccountService;
import io.munib.service.LoggerService;
import io.munib.util.MoneyUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final LoggerService logger;

    @Transactional
    @Override
    public Long createAccount(boolean initializeAllCurrencies) {
        Account account = Account.builder().build();
        if (initializeAllCurrencies) { // initializeAllCurrencies to avoid unnecessary account creation
            for (Currency c : Currency.values()) {
                account.getOrCreateBalance(c);
            }
        }
        accountRepository.save(account);
        return account.getId();
    }

    @Transactional
    @Override
    public BalanceResponse getBalance(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new APIException(ResponseCode.RESOURCE_NOT_FOUND, "Account not found: " + accountId));
        Map<Currency, BigDecimal> map = new EnumMap<>(Currency.class);
        for (AccountBalance b : account.getBalances()) {
            map.put(b.getCurrency(), MoneyUtils.toMajorUnits(b.getAmountMinor()));
        }
        for (Currency c : Currency.values()) {
            // if initializeAllCurrencies was false on the time of new account creation, simply avoid unnecessary DB entries
            map.putIfAbsent(c, MoneyUtils.toMajorUnits(0L));
        }
        return new BalanceResponse(accountId, map);
    }

    @Transactional
    @Override
    public BalanceResponse credit(Long accountId, Currency currency, long amountMinor) {
        if (amountMinor <= 0) {
            throw new APIException(ResponseCode.INVALID_REQUEST, "Amount must be > 0");
        }
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new APIException(ResponseCode.RESOURCE_NOT_FOUND, "Account not found: " + accountId));
        // get/create account balance of the currency if credit called
        AccountBalance bal = account.getOrCreateBalance(currency);

        // Math.addExact because if overflow happens, there'll be an exception
        // if we just add using + ; if overflow will happen, it'll be a negative number
        bal.setAmountMinor(Math.addExact(bal.getAmountMinor(), amountMinor));
        accountRepository.save(account);
        return getBalance(accountId);
    }

    @Transactional
    @Override
    public BalanceResponse debit(Long accountId, Currency currency, long amountMinor) {
        logger.log("debit request: accountId=" + accountId + ", currency=" + currency + ", amountMinor=" + amountMinor);
        if (amountMinor <= 0) {
            throw new APIException(ResponseCode.INVALID_REQUEST, "Amount must be > 0");
        }
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new APIException(ResponseCode.RESOURCE_NOT_FOUND, "Account not found: " + accountId));
        AccountBalance bal = account.getOrCreateBalance(currency);
        if (bal.getAmountMinor() < amountMinor) {
            throw new APIException(ResponseCode.INSUFFICIENT_FUNDS, "Insufficient funds in " + currency);
        }
        bal.setAmountMinor(Math.subtractExact(bal.getAmountMinor(), amountMinor));
        accountRepository.save(account);
        return getBalance(accountId);
    }
}