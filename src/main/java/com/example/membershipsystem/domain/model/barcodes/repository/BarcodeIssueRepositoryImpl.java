package com.example.membershipsystem.domain.model.barcodes.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import static com.example.membershipsystem.domain.model.barcodes.entity.QBarcode.barcode;
import static com.example.membershipsystem.domain.model.barcodes.entity.QBarcodeIssue.barcodeIssue;

public class BarcodeIssueRepositoryImpl implements BarcodeIssueRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public BarcodeIssueRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public boolean existByBarcodeNo(String barcodeNo) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(barcodeIssue)
                .join(barcodeIssue.barcode, barcode)
                .where(barcode.barcodeNo.eq(barcodeNo),
                        barcodeIssue.useYn.eq(true),
                        barcodeIssue.delYn.eq(false))
                .fetchFirst();

        return fetchOne != null;
    }
}
