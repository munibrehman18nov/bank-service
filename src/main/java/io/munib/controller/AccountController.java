package io.munib.controller;

import io.munib.dto.BalanceResponse;
import io.munib.dto.CreateAccountRequest;
import io.munib.dto.MoneyRequest;
import io.munib.service.AccountService;
import io.munib.swaggerdoc.controller.AccountApi;
import io.munib.util.MoneyUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/accounts")
@RestController
public class AccountController implements AccountApi {
    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Long> create(@Valid @RequestBody CreateAccountRequest req) {
        Long id = accountService.createAccount(Boolean.TRUE.equals(req.initializeAllCurrencies()));
        return Map.of("accountId", id);
    }

    @GetMapping("/{accountId}/balance")
    public BalanceResponse balance(@PathVariable Long accountId) {
        return accountService.getBalance(accountId);
    }

    @PostMapping("/{accountId}/credit")
    public BalanceResponse credit(@PathVariable Long accountId, @Valid @RequestBody MoneyRequest req) {
        return accountService.credit(accountId, req.currency(), MoneyUtils.toMinorUnits(req.amount()));
    }

    @PostMapping("/{accountId}/debit")
    public BalanceResponse debit(@PathVariable Long accountId, @Valid @RequestBody MoneyRequest req) {
        return accountService.debit(accountId, req.currency(), MoneyUtils.toMinorUnits(req.amount()));
    }
}