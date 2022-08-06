package com.example.membershipsystem.application.point;

import com.example.membershipsystem.presentation.points.request.PointEarningRequest;
import com.example.membershipsystem.presentation.points.request.PointUsingRequest;
import com.example.membershipsystem.application.point.response.PointReturnDto;

public interface PointService {
    PointReturnDto earnPoint(PointEarningRequest pointCollectionRequest);

    PointReturnDto usePoint(PointUsingRequest pointUsingRequest);
}
