package com.example.membershipsystem.domain.model.points.entity;

import com.example.membershipsystem.domain.model.categorys.entity.Store;
import com.example.membershipsystem.domain.model.barcodes.entity.BarcodeIssue;
import com.example.membershipsystem.domain.model.points.enums.PointClassType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "point_history")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "barcode_issue_id", referencedColumnName = "id")
    private BarcodeIssue barcodeIssue;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private Store store;

    @Column(name = "amt", nullable = false)
    private Long amt;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PointClassType type;

    @Column(name = "approve_at", nullable = false)
    @CreatedDate
    private LocalDate approveAt;

    @Column(name = "del_yn", nullable = false)
    private boolean delYn;

    public PointHistory(BarcodeIssue barcodeIssue, Store store, PointClassType pointClassType, Long amt) {
        this.barcodeIssue = barcodeIssue;
        this.store = store;
        this.type = pointClassType;
        this.amt = amt;
    }
}
