package io.munib.swaggerdoc.controller;

import io.munib.dto.BalanceResponse;
import io.munib.dto.CreateAccountRequest;
import io.munib.dto.MoneyRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@Tag(name = "Accounts")
public interface AccountApi {

    @Operation(summary = "Create an account")
    @ResponseStatus(HttpStatus.CREATED)
    Map<String, Long> create(CreateAccountRequest req);

    @Operation(summary = "Get balances for an account (per currency)")
    BalanceResponse balance(Long accountId) throws Exception;

    @Operation(summary = "Credit / add money to an account (single currency)")
    BalanceResponse credit(Long accountId, MoneyRequest req) throws Exception;

    @Operation(summary = "Debit money from an account (single currency, no auto FX). Calls external system before debiting.")
    @PostMapping("/{accountId}/debit")
    BalanceResponse debit(Long accountId, MoneyRequest req) throws Exception;
}