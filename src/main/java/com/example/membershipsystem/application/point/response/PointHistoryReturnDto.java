package com.example.membershipsystem.application.point.response;

import com.example.membershipsystem.domain.model.points.enums.PointClassType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
public class PointHistoryReturnDto {
    private Long pointHistoryId;
    private String barcodeNo;
    private Long storeId;
    @Enumerated(EnumType.STRING)
    private PointClassType type;
    private LocalDate approveAt;
    private Long amt;

    @QueryProjection
    public PointHistoryReturnDto(Long pointHistoryId, String barcodeNo
            , Long storeId, PointClassType type, LocalDate approveAt, Long amt) {
        this.pointHistoryId = pointHistoryId;
        this.barcodeNo = barcodeNo;
        this.storeId = storeId;
        this.type = type;
        this.approveAt = approveAt;
        this.amt = amt;
    }
}
