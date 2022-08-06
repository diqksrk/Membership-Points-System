package com.example.membershipsystem.application.barcode.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BarcodeIssueReturnDto {
    private Long id;
    private Long userId;
    private String barcodeNo;
    private LocalDate issueDate;
    private LocalDate terminationDate;
    private String statusCode;
}
