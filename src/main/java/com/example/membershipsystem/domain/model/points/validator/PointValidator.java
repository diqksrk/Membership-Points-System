package com.example.membershipsystem.domain.model.points.validator;

import com.example.membershipsystem.domain.model.categorys.repository.StoreRepository;
import com.example.membershipsystem.presentation.points.request.PointUsingRequest;
import com.example.membershipsystem.domain.model.barcodes.repository.BarcodeIssueRepository;
import com.example.membershipsystem.infrastructure.exception.message.PointExceptionMessage;
import com.example.membershipsystem.presentation.points.request.PointEarningRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class PointValidator {
    private final BarcodeIssueRepository barcodeIssueRepository;
    private final StoreRepository storeRepository;

    public void earnPointvalidate(Object target, Errors errors) {
        PointEarningRequest pointEarningRequest = (PointEarningRequest) target;

        commonPointValidateCheck(errors, pointEarningRequest.getBarcodeNo(), pointEarningRequest.getStoreId());
    }

    public void usePointvalidate(Object target, Errors errors) {
        PointUsingRequest pointUsingRequest = (PointUsingRequest) target;

        commonPointValidateCheck(errors, pointUsingRequest.getBarcodeNo(), pointUsingRequest.getStoreId());
    }

    protected void commonPointValidateCheck(Errors errors, String barcodeNo, Long storeId) {
        if (!isRigthBarcodeLength(barcodeNo)) {
            errors.reject(PointExceptionMessage.WrongBarcodeException.getType(), PointExceptionMessage.WrongBarcodeException.getMessage());
            return;
        }

        if (!isRigthStore(storeId, true, false)) {
            errors.reject(PointExceptionMessage.NotFoundStoreException.getType(), PointExceptionMessage.NotFoundStoreException.getMessage());
            return;
        }

        if (!isValidBarcode(barcodeNo)) {
            errors.reject(PointExceptionMessage.NotFoundBarcodeException.getType(), PointExceptionMessage.NotFoundBarcodeException.getMessage());
            return;
        }
    }

    private boolean isValidBarcode(String barcodeNo) {
        if (!barcodeIssueRepository.existByBarcodeNo(barcodeNo)) {
            return false;
        }

        return true;
    }

    private boolean isRigthStore(Long storeId, boolean useYn, boolean delYn) {
        if (!storeRepository.existsByIdAndUseYnAndDelYn(storeId, useYn, delYn)) {
            return false;
        }

        return true;
    }

    private boolean isRigthBarcodeLength(String barcodeNo) {
        if (barcodeNo.length() != 10) {
            return false;
        }

        return true;
    }
}
