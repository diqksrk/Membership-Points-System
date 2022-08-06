package com.example.membershipsystem.domain.model.points.repository;

import com.example.membershipsystem.application.point.response.PointHistoryReturnDto;
import com.example.membershipsystem.application.point.response.QPointHistoryReturnDto;
import com.example.membershipsystem.presentation.points.request.PointSearchingReqeust;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.membershipsystem.domain.model.barcodes.entity.QBarcode.barcode;
import static com.example.membershipsystem.domain.model.barcodes.entity.QBarcodeIssue.barcodeIssue;
import static com.example.membershipsystem.domain.model.points.entity.QPointHistory.pointHistory;

public class PointHistoryRepositoryImpl implements PointHistoryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public PointHistoryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<PointHistoryReturnDto> selectPointHistory(PointSearchingReqeust pointSearchingReqeust, Pageable pageable) {
        List<PointHistoryReturnDto> results = queryFactory
                .select(new QPointHistoryReturnDto(
                        pointHistory.id,
                        barcode.barcodeNo,
                        pointHistory.store.id,
                        pointHistory.type,
                        pointHistory.approveAt,
                        pointHistory.amt
                ))
                .from(pointHistory)
                .leftJoin(pointHistory.barcodeIssue, barcodeIssue)
                .leftJoin(barcodeIssue.barcode, barcode)
                .on(barcodeIssue.barcode.barcodeNo.eq(pointSearchingReqeust.getBarcodeNo()))
                .where(pointHistory.delYn.eq(false),
                        pointHistory.approveAt.goe(pointSearchingReqeust.getStartDate()),
                        pointHistory.approveAt.loe(pointSearchingReqeust.getEndDate())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(results, pageable, results.size());
    }
}
