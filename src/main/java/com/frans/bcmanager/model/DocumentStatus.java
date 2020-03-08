package com.frans.bcmanager.model;

import lombok.Getter;

@Getter
public enum DocumentStatus {
    PAID,
    NOT_PAID,
    NOT_RELEVANT;

    private DocumentStatus nextStatus;
    private String styleClass;

    static {
        PAID.nextStatus = NOT_PAID;
        PAID.styleClass = "text-success";
        NOT_PAID.nextStatus = PAID;
        NOT_PAID.styleClass = "text-danger";
        NOT_RELEVANT.nextStatus = NOT_RELEVANT;
    }
}