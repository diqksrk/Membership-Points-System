package com.example.membershipsystem.domain.model.barcodes.repository;

import com.example.membershipsystem.domain.model.barcodes.entity.Barcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BarcodeRepository extends JpaRepository<Barcode, Long> {
    @Query(value = "SELECT * FROM barcodes d WHERE align_yn = false And use_yn = true AND del_yn = false order by RAND() limit 1  FOR UPDATE nowait", nativeQuery = true)
    Optional<Barcode> getBarcodeNoWithLock();
}
