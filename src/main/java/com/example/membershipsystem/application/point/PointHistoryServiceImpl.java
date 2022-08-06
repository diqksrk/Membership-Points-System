package com.example.membershipsystem.application.point;

import com.example.membershipsystem.application.point.response.PointHistoryReturnDto;
import com.example.membershipsystem.domain.model.points.repository.PointHistoryRepository;
import com.example.membershipsystem.presentation.points.request.PointSearchingReqeust;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointHistoryServiceImpl implements PointHistoryService {
    private final PointHistoryRepository pointHistoryRepository;

    @Override
    public Page<PointHistoryReturnDto> searchPointHistory(PointSearchingReqeust pointSearchingReqeust, Pageable pageable) {
        return pointHistoryRepository.selectPointHistory(pointSearchingReqeust, pageable);
    }
}
