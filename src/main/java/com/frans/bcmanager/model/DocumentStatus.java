package com.frans.bcmanager.model;

import lombok.Getter;

@Getter
public enum DocumentStatus {
    PAID,
    NOT_PAID,
    NOT_ACCEPTED,
    ACCEPTED;

    private DocumentStatus nextStatus;
    private String styleClass;

    static {
        PAID.nextStatus = NOT_PAID;
        NOT_PAID.nextStatus = PAID;
        NOT_ACCEPTED.nextStatus = ACCEPTED;
        ACCEPTED.nextStatus = NOT_ACCEPTED;
        PAID.styleClass = "text-success required";
        NOT_PAID.styleClass = "text-danger required";
        ACCEPTED.styleClass = "text-success required";
        NOT_ACCEPTED.styleClass = "text-danger required";
    }
}
