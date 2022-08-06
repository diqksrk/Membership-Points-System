package com.example.membershipsystem.domain.model.points.repository;

import com.example.membershipsystem.domain.model.points.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
    @Query(value = "SELECT p.*" +
            " FROM points p" +
            "     ,stores s" +
            "     ,barcodes b" +
            "     ,barcode_issue i " +
            "WHERE p.barcode_issue_id = i.id" +
            "  AND b.barcode_no = :barcodeNo" +
            "  AND i.barcode_id = b.id" +
            "  AND s.id = :storeId" +
            "  AND s.category = p.category" +
            "  AND p.del_yn = false FOR UPDATE", nativeQuery = true)
    Optional<Point> getExistPointInfo(@Param("barcodeNo") String barcodeNo, @Param("storeId") Long storeId);
}
