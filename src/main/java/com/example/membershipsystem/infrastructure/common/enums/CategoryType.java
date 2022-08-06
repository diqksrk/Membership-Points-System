package com.example.membershipsystem.infrastructure.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CategoryType {
    A("A", "백화점"),
    B("B", "마트"),
    C("C", "식료품점");

    private String name;
    private String description;
}
