package com.example.membershipsystem.infrastructure.exception;

import com.example.membershipsystem.infrastructure.error.ErrorsResource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

public class GlobalRequestException {
    public static ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }
}
