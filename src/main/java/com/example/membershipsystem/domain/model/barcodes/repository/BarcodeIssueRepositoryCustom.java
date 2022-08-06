package com.example.membershipsystem.domain.model.barcodes.repository;

public interface BarcodeIssueRepositoryCustom {
    boolean existByBarcodeNo(String barcodeNo);
}
