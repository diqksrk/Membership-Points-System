package com.example.membershipsystem.domain.model.barcodes.entity;

import com.example.membershipsystem.domain.model.barcodes.enums.IssueStatusType;
import com.example.membershipsystem.domain.model.users.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "barcode_issue")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BarcodeIssue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "barcode_id", referencedColumnName = "id")
    private Barcode barcode;

    @Column(name = "issue_date")
    @CreatedDate
    private LocalDate issueDate;

    @Column(name = "termination_date")
    private LocalDate terminationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_code", nullable = false)
    private IssueStatusType statusCode;

    @Column(name = "use_yn", nullable = false)
    private boolean useYn;

    @Column(name = "del_yn", nullable = false)
    private boolean delYn;

    public BarcodeIssue(Long userId) {
        this.user = new User(userId);
    }

    public void setInitParam(Barcode barcode) {
        this.barcode = barcode;
        this.statusCode = IssueStatusType.issued;
        this.delYn = false;
        this.useYn = true;
    }
}
