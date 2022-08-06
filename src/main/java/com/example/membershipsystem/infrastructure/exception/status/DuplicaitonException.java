package com.example.membershipsystem.infrastructure.exception.status;

import com.example.membershipsystem.infrastructure.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class DuplicaitonException extends GlobalException {
    public DuplicaitonException(HttpStatus status) {
        super(HttpStatus.NOT_ACCEPTABLE);
    }

    public DuplicaitonException(String reason) {
        super(HttpStatus.NOT_ACCEPTABLE, reason);
    }

    public DuplicaitonException(String reason, Throwable cause) {
        super(HttpStatus.NOT_ACCEPTABLE, reason, cause);
    }
}
