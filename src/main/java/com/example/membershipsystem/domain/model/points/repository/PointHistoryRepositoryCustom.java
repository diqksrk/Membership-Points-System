package com.example.membershipsystem.domain.model.points.repository;

import com.example.membershipsystem.application.point.response.PointHistoryReturnDto;
import com.example.membershipsystem.presentation.points.request.PointSearchingReqeust;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PointHistoryRepositoryCustom {
    Page<PointHistoryReturnDto> selectPointHistory(PointSearchingReqeust pointSearchingReqeust, Pageable pageable);
}
