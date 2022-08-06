package com.example.membershipsystem.infrastructure.exception.status;

import com.example.membershipsystem.infrastructure.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class NotFoundDataException extends GlobalException {
    public NotFoundDataException(HttpStatus status) {
        super(HttpStatus.NOT_FOUND);
    }

    public NotFoundDataException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }

    public NotFoundDataException(String reason, Throwable cause) {
        super(HttpStatus.NOT_FOUND, reason, cause);
    }
}
