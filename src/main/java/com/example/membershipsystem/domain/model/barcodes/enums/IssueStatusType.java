package com.example.membershipsystem.domain.model.barcodes.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum IssueStatusType {
    issued("issued", "발급"),
    issuing("issuing", "발급중"),
    termination("termination", "종료");

    private String name;
    private String description;
}
