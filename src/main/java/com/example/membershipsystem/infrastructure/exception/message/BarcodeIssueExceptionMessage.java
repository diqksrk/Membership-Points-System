package com.example.membershipsystem.infrastructure.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum  BarcodeIssueExceptionMessage {
    NotFoundException("NotFoundException", "해당 사용자는 멤버십 회원이 아닙니다."),
    WrongUserException("WrongUserException", "멤버십 사용자 번호를 확인해주세요."),
    NotBarcodeFoundException("NotBarcodeFoundException", "바코드가 없습니다.");

    private String type;
    private String message;
}
