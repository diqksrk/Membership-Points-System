package com.example.membershipsystem.presentation.points.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor @AllArgsConstructor
@Builder
public class PointUsingRequest {
    @NotNull(message = "상점 아이디를 입력해주세요.")
    @ApiModelProperty(example = "1")
    private Long storeId;
    @NotBlank(message = "바코드 숫자를 입력해주세요.")
    @ApiModelProperty(example = "1000000000")
    private String barcodeNo;
    @NotNull(message = "포인트 사용금를 입력해주세요.")
    @ApiModelProperty(example = "3000")
    private Long useAmt;
}
