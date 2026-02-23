package io.munib.common;

import io.munib.error.APIException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(APIException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(APIException ex, HttpServletRequest request) {
        log.warn("APIException [{}]: {}", ex.getCode(), ex.getMessage(), ex);

        return build(ex.getCode(), request.getRequestURI());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleRuntime(RuntimeException ex, HttpServletRequest request) {
        log.error("Unhandled runtime exception", ex);

        return build(ResponseCode.INTERNAL_ERROR, request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception", ex);

        return build(ResponseCode.INTERNAL_ERROR, request.getRequestURI());
    }

    private ResponseEntity<ApiErrorResponse> build(ResponseCode responseCode, String path) {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .timestamp(Instant.now())
                .status(responseCode.getHttpStatus().value())
                .error(responseCode.getHttpStatus().getReasonPhrase())
                .code(responseCode.getCode())
                .message(responseCode.getMessage())
                .path(path)
                .build();

        return ResponseEntity.status(responseCode.getHttpStatus()).body(response);
    }
}