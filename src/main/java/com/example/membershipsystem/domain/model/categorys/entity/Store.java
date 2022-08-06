package com.example.membershipsystem.domain.model.categorys.entity;

import com.example.membershipsystem.infrastructure.common.enums.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "stores")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "partner_name", nullable = false)
    private String partnerName;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private CategoryType category;

    @Column(name = "use_yn", nullable = false)
    private boolean useYn;

    @Column(name = "del_yn", nullable = false)
    private boolean delYn;
}
