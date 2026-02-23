package io.munib.controller;

import io.munib.dto.BalanceResponse;
import io.munib.dto.ExchangeRequest;
import io.munib.service.AccountService;
import io.munib.service.ExchangeService;
import io.munib.swaggerdoc.controller.ExchangeApi;
import io.munib.util.MoneyUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "FX Exchange")
@RestController
@RequestMapping("/api/accounts/{accountId}/fx")
@RequiredArgsConstructor
public class ExchangeController implements ExchangeApi {

    private final ExchangeService exchangeService;
    private final AccountService accountService;

    @Operation(summary = "Exchange currency using fixed value (explicit). Debits 'from' and credits 'to'.")
    @PostMapping("/exchange")
    public BalanceResponse exchange(@PathVariable Long accountId, @Valid @RequestBody ExchangeRequest req) {
        exchangeService.exchange(accountId, req.from(), req.to(), MoneyUtils.toMinorUnits(req.amount()));
        return accountService.getBalance(accountId);
    }
}