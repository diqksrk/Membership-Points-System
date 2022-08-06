package com.example.membershipsystem.domain.model.points.entity;

import com.example.membershipsystem.domain.model.barcodes.entity.BarcodeIssue;
import com.example.membershipsystem.infrastructure.common.enums.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "points")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "barcode_issue_id", referencedColumnName = "id")
    private BarcodeIssue barcodeIssue;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private CategoryType category;

    @Column(name = "reserve_amt", nullable = false)
    private Long reserveAmt;

    @Column(name = "reg_date")
    @CreatedDate
    private LocalDate regDate;

    @Column(name = "del_yn", nullable = false)
    private boolean delYn;

    public Point(BarcodeIssue barcodeIssue, CategoryType category, Long reserveAmt) {
        this.barcodeIssue = barcodeIssue;
        this.category = category;
        this.reserveAmt = reserveAmt;
        this.delYn = false;
    }

    public void addReserveAmt(Long reserveAmt) {
        this.reserveAmt += reserveAmt;
    }

    public void minusReserveAmt(Long reserveAmt) {
        this.reserveAmt -= reserveAmt;
    }
}
