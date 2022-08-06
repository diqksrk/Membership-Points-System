package com.example.membershipsystem.application.point;

import com.example.membershipsystem.domain.model.barcodes.entity.BarcodeIssue;
import com.example.membershipsystem.domain.model.barcodes.repository.BarcodeIssueRepository;
import com.example.membershipsystem.domain.model.categorys.entity.Store;
import com.example.membershipsystem.domain.model.categorys.repository.StoreRepository;
import com.example.membershipsystem.domain.model.points.entity.PointHistory;
import com.example.membershipsystem.domain.model.points.repository.PointHistoryRepository;
import com.example.membershipsystem.infrastructure.exception.status.DuplicaitonException;
import com.example.membershipsystem.presentation.points.request.PointEarningRequest;
import com.example.membershipsystem.presentation.points.request.PointUsingRequest;
import com.example.membershipsystem.application.point.response.PointReturnDto;
import com.example.membershipsystem.domain.model.points.entity.Point;
import com.example.membershipsystem.domain.model.points.enums.PointClassType;
import com.example.membershipsystem.domain.model.points.repository.PointRepository;
import com.example.membershipsystem.infrastructure.exception.message.PointExceptionMessage;
import com.example.membershipsystem.infrastructure.exception.status.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
    private final PointRepository pointRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final BarcodeIssueRepository barcodeIssueRepository;
    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public PointReturnDto earnPoint(PointEarningRequest pointEarningRequest) {
        saveEarnPointHistory(pointEarningRequest);

        // 이미 업종별 Point 총합이 존재하면 사용할때 동시에 사용하지 못하도록 Lock을 건다.
        Optional<Point> point = pointRepository.getExistPointInfo(pointEarningRequest.getBarcodeNo(), pointEarningRequest.getStoreId());
        if (!point.isPresent()) {
            // 다중으로 insert 되는걸 막기 위해 lock을 검.
            Optional<BarcodeIssue> barcodeIssue = barcodeIssueRepository.findByBarcodeNoWithLock(pointEarningRequest.getBarcodeNo());
            Optional<Store> store = storeRepository.findById(pointEarningRequest.getStoreId());
            Point updatePoint = new Point(barcodeIssue.get(), store.get().getCategory(), pointEarningRequest.getReserveAmt());

            // 이후 기다렸다 해제되는 트랜잭션들이 이미 등록되어 있으면 Exception발생 시키기 위해서 다시 한번 조회
            if (pointRepository.getExistPointInfo(pointEarningRequest.getBarcodeNo(), pointEarningRequest.getStoreId()).isPresent()) {
                throw new DuplicaitonException(PointExceptionMessage.DuplicationPointException.getMessage());
            }

            Point updatedPoint = pointRepository.save(updatePoint);
            return new PointReturnDto(updatedPoint.getId(), pointEarningRequest.getBarcodeNo(), updatedPoint.getCategory().getName(), updatedPoint.getReserveAmt());
        }

        point.get().addReserveAmt(pointEarningRequest.getReserveAmt());
        Point resevedPoint = pointRepository.save(point.get());
        return new PointReturnDto(resevedPoint.getId(), pointEarningRequest.getBarcodeNo(), resevedPoint.getCategory().getName(), resevedPoint.getReserveAmt());
    }

    @Override
    @Transactional
    public PointReturnDto usePoint(PointUsingRequest pointUsingRequest) {
        saveUsePointHistory(pointUsingRequest);

        // 이미 업종별 Point 총합이 존재하면 사용할때 동시에 사용하지 못하도록 Lock을 건다.
        Optional<Point> point = pointRepository.getExistPointInfo(pointUsingRequest.getBarcodeNo(), pointUsingRequest.getStoreId());
        if (!point.isPresent() || point.get().getReserveAmt() - pointUsingRequest.getUseAmt() < 0) {
            throw new UnauthorizedException(PointExceptionMessage.UnAuthorizedPointUseException.getMessage());
        }

        Point updatePoint = point.get();
        updatePoint.minusReserveAmt(pointUsingRequest.getUseAmt());
        Point updatedPoint = pointRepository.save(updatePoint);

        return new PointReturnDto(updatedPoint.getId(), pointUsingRequest.getBarcodeNo(), updatedPoint.getCategory().getName(), updatedPoint.getReserveAmt());
    }

    @Transactional
    protected void saveEarnPointHistory(PointEarningRequest pointEarningRequest) {
        Optional<BarcodeIssue> byBarcodeNo = barcodeIssueRepository.findByBarcodeNo(pointEarningRequest.getBarcodeNo());
        Store storeReferenceById = storeRepository.getReferenceById(pointEarningRequest.getStoreId());
        PointHistory newPointHistory = new PointHistory(byBarcodeNo.get(), storeReferenceById, PointClassType.earn, pointEarningRequest.getReserveAmt());
        pointHistoryRepository.save(newPointHistory);
    }

    @Transactional
    protected void saveUsePointHistory(PointUsingRequest pointUsingRequest) {
        Optional<BarcodeIssue> byBarcodeNo = barcodeIssueRepository.findByBarcodeNo(pointUsingRequest.getBarcodeNo());
        Store storeReferenceById = storeRepository.getReferenceById(pointUsingRequest.getStoreId());
        PointHistory newPointHistory = new PointHistory(byBarcodeNo.get(), storeReferenceById, PointClassType.earn, pointUsingRequest.getUseAmt());
        pointHistoryRepository.save(newPointHistory);
    }
}
