package com.frans.bcmanager.enums;

import lombok.Getter;

@Getter
public enum SplitMode {
    NONE("100%"),
    SPLIT4("30% - 30% - 30% - 10%");

    private String value;

    SplitMode(String value) {
        this.value = value;
    }
}
