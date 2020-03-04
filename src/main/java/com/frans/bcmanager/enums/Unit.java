package com.frans.bcmanager.enums;

import lombok.Getter;

@Getter
public enum Unit {

    FF("ff"),
    M("m"),
    M2("m²"),
    M3("m³"),
    PCE("pce"),
    H("h");

    private String displayName;

    Unit(String unit) {

        this.displayName = unit;
    }

    public String getDisplayName() {
        return displayName;
    }
}
