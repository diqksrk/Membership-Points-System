package com.example.membershipsystem.domain.model.barcodes.repository;

import com.example.membershipsystem.domain.model.barcodes.entity.BarcodeIssue;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BarcodeIssueRepository extends JpaRepository<BarcodeIssue, Long>, BarcodeIssueRepositoryCustom {
    @EntityGraph(attributePaths = {"barcode"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("select c from BarcodeIssue c left join c.barcode where c.user.id = :userId")
    Optional<BarcodeIssue> findByUserId(@Param("userId") Long userId);

    @EntityGraph(attributePaths = {"barcode"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("select c from BarcodeIssue c left join c.barcode where c.barcode.barcodeNo = :barcodeNo")
    Optional<BarcodeIssue> findByBarcodeNo(@Param("barcodeNo") String barcodeNo);

    @Query(value = "SELECT i.*" +
            " FROM barcodes b" +
            "     ,barcode_issue i " +
            "WHERE b.barcode_no = :barcodeNo" +
            "  AND i.barcode_id = b.id FOR UPDATE", nativeQuery = true)
    Optional<BarcodeIssue> findByBarcodeNoWithLock(@Param("barcodeNo") String barcodeNo);
}
