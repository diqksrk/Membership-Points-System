package com.example.membershipsystem.infrastructure.error;

import org.springframework.hateoas.EntityModel;
import org.springframework.validation.Errors;

public class ErrorsResource extends EntityModel<Errors> {
    public ErrorsResource(Errors content) {
        super(content);
    }
}
