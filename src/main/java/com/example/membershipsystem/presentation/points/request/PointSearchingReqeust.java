package com.example.membershipsystem.presentation.points.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class PointSearchingReqeust {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(example = "2022-05-01")
    LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(example = "2023-01-01")
    LocalDate endDate;
    @ApiModelProperty(example = "1000000000")
    String barcodeNo;
}
