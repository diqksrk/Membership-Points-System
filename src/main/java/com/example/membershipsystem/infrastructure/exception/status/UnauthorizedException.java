package com.example.membershipsystem.infrastructure.exception.status;

import com.example.membershipsystem.infrastructure.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends GlobalException {
    public UnauthorizedException(HttpStatus status) {
        super(HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(String reason) {
        super(HttpStatus.UNAUTHORIZED, reason);
    }

    public UnauthorizedException(String reason, Throwable cause) {
        super(HttpStatus.UNAUTHORIZED, reason, cause);
    }
}
