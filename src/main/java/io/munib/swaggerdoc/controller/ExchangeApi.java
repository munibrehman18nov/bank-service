package io.munib.swaggerdoc.controller;

import io.munib.dto.BalanceResponse;
import io.munib.dto.ExchangeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "FX Exchange")
public interface ExchangeApi {

    @Operation(
            summary = "Exchange currency using fixed value (explicit). " +
                    "Debits 'from' currency and credits 'to' currency. " +
                    "No automatic FX during debit."
    )
    BalanceResponse exchange(Long accountId, ExchangeRequest req);
}