package com.example.membershipsystem.presentation.barcodes.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
@Builder @AllArgsConstructor
public class BarcodeIssueRequest {
    @NotNull(message = "유저 아이디를 입력해주세요.")
    @ApiModelProperty(example = "100000000")
    private Long userId;
}
