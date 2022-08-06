package com.example.membershipsystem.domain.model.barcodes.validator;

import com.example.membershipsystem.infrastructure.exception.message.BarcodeIssueExceptionMessage;
import com.example.membershipsystem.presentation.barcodes.request.BarcodeIssueRequest;
import com.example.membershipsystem.domain.model.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class BarcodeIssueValidator {
    private final UserRepository userRepository;

    public void barcodeIssueValidate(BarcodeIssueRequest barcodeIssueRequest, Errors errors) {
        if (barcodeIssueRequest.getUserId() < 100000000) {
            errors.reject(BarcodeIssueExceptionMessage.WrongUserException.getType(), BarcodeIssueExceptionMessage.WrongUserException.getMessage());
            return;
        }

        if (!userRepository.existsById(barcodeIssueRequest.getUserId())) {
            errors.reject(BarcodeIssueExceptionMessage.NotFoundException.getType(), BarcodeIssueExceptionMessage.NotFoundException.getMessage());
            return;
        }
    }
}
