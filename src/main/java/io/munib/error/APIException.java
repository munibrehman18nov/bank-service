package io.munib.error;

import io.munib.common.ResponseCode;
import lombok.Getter;

@Getter
public class APIException extends RuntimeException {

    private final ResponseCode code;

    public APIException(ResponseCode code, String message) {
        super(message);
        this.code = code;
    }

    public APIException(ResponseCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
