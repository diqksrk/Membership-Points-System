package com.example.membershipsystem.infrastructure.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PointExceptionMessage {
    WrongBarcodeException("WrongBarcodeException", "바코드 번호를 확인해주세요."),
    NotFoundBarcodeException("NotFoundBarcodeException", "해당 바코드는 등록된 바코드가 아닙니다."),
    NotFoundStoreException("NotFoundStoreException", "해당 영업장은 제휴된 영업장이 아닙니다."),
    UnAuthorizedPointUseException("UnAuthorizedPointUseException", "포인트가 부족합니다."),
    DuplicationPointException("DuplicationPointException", "이미 적립되었습니다.");

    private String type;
    private String message;
}
