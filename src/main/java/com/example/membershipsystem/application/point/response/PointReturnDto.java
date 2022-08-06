package com.example.membershipsystem.application.point.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PointReturnDto {
    private Long id;
    private String barcodeNo;
    private String category;
    private Long reserveAmt;
}
