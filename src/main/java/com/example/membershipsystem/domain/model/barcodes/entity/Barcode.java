package com.example.membershipsystem.domain.model.barcodes.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "barcodes")
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Barcode {
    @Id
    private Long id;

    @Column(name = "barcode_no", nullable = false)
    private String barcodeNo;

    @Column(name = "align_yn", nullable = false)
    private boolean alignYn;

    @Column(name = "align_date", nullable = false)
    private LocalDate alignDate;

    @Column(name = "reg_date", nullable = false)
    private LocalDate regDate;

    @Column(name = "use_yn", nullable = false)
    private boolean useYn;

    @Column(name = "del_yn", nullable = false)
    private boolean delYn;

    public void changeToisAlign() {
        this.alignYn = true;
        this.alignDate = LocalDate.now();
    }
}
