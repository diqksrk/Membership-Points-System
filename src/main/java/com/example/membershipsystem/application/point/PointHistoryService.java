package com.example.membershipsystem.application.point;

import com.example.membershipsystem.application.point.response.PointHistoryReturnDto;
import com.example.membershipsystem.presentation.points.request.PointSearchingReqeust;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PointHistoryService {
    Page<PointHistoryReturnDto> searchPointHistory(PointSearchingReqeust pointSearchingReqeust, Pageable pageable);
}
