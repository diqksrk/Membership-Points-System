package com.example.membershipsystem.infrastructure.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum  PointHistoryExceptionMessage {
    WrongBarcodeException("WrongBarcodeException", "바코드 번호를 확인해주세요."),
    WrongStartDateException("WrongStartDateException", "시작 일자를 확인해주세요."),
    WrongEndDateException("WrongEndDateException", "종료 일자를 확인해주세요"),
    WrongDateDiffException("WrongDateDiffException", "종료일자는 시작일자보다 앞설수 없습니다.");

    private String type;
    private String message;
}
