package com.example.membershipsystem.domain.model.points.validator;

import com.example.membershipsystem.presentation.points.request.PointSearchingReqeust;
import com.example.membershipsystem.infrastructure.exception.message.PointHistoryExceptionMessage;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class PointHistoryValidator {
    public void searchPointHistoryValidate(PointSearchingReqeust pointSearchingReqeust, Errors errors) {
        if (pointSearchingReqeust.getStartDate() == null) {
            errors.reject(PointHistoryExceptionMessage.WrongStartDateException.getType(), PointHistoryExceptionMessage.WrongStartDateException.getMessage());
            return;
        }

        if (pointSearchingReqeust.getEndDate() == null) {
            errors.reject(PointHistoryExceptionMessage.WrongEndDateException.getType(), PointHistoryExceptionMessage.WrongEndDateException.getMessage());
            return;
        }

        if (pointSearchingReqeust.getBarcodeNo() == null || pointSearchingReqeust.getBarcodeNo().length() != 10) {
            errors.reject(PointHistoryExceptionMessage.WrongBarcodeException.getType(), PointHistoryExceptionMessage.WrongBarcodeException.getMessage());
            return;
        }

        if (pointSearchingReqeust.getEndDate().isBefore(pointSearchingReqeust.getStartDate())) {
            errors.reject(PointHistoryExceptionMessage.WrongDateDiffException.getType(), PointHistoryExceptionMessage.WrongDateDiffException.getMessage());
            return;
        }
    }
}
