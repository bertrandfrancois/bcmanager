package com.frans.bcmanager.enums;

import lombok.Getter;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

public enum TaxRate {

    T0(0),
    T6(0.06),
    T12(0.12),
    T21(0.21);

    private BigDecimal value;

    TaxRate(double value) {
        this.value = BigDecimal.valueOf(value);
    }

    @NumberFormat(style= NumberFormat.Style.PERCENT)
    public BigDecimal getValue() {
        return value;
    }
}
