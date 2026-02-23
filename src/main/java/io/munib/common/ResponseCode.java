package io.munib.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {

    INTERNAL_ERROR(
            "500",
            "Internal server error",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),
    INVALID_REQUEST(
            "400",
            "Invalid request",
            HttpStatus.BAD_REQUEST
    ),

    INSUFFICIENT_FUNDS(
            "002",
            "Insufficient funds",
            HttpStatus.UNPROCESSABLE_ENTITY
    ),
    CURRENCY_NOT_SUPPORTED(
            "003",
            "Currency not supported",
            HttpStatus.BAD_REQUEST
    ),
    RESOURCE_NOT_FOUND(
            "001",
            "Resource not found",
            HttpStatus.NOT_FOUND
    ),
    EXTERNAL_SERVICE_ERROR(
            "005",
            "External service failure",
            HttpStatus.BAD_GATEWAY
    ),
    EXTERNAL_TIMEOUT(
            "006",
            "External service timeout",
            HttpStatus.GATEWAY_TIMEOUT
    ),
    INVALID_FX_RATE_CONFIG(
            "007",
            "Invalid Fx rate configuration",
            HttpStatus.UNPROCESSABLE_ENTITY
    ),
    FX_RATE_NOT_FOUND(
            "008",
            "Fx rate not found",
            HttpStatus.BAD_REQUEST
    );

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}