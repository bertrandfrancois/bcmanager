package com.frans.bcmanager.enums;

import lombok.Getter;

@Getter
public enum DocumentType {
    ESTIMATE("ESTIMATE"),
    INVOICE("INVOICE");

    private String type;

    DocumentType(String type) {
        this.type = type;
    }

}
