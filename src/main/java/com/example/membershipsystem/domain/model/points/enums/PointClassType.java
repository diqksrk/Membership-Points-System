package com.example.membershipsystem.domain.model.points.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PointClassType {
    earn("earn", "적립"),
    use("use", "사용");

    private String name;
    private String description;
}
