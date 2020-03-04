package com.frans.bcmanager.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Data
public class Price {

    @Column(name = "PRICE")
    private BigDecimal price;

    public Price(BigDecimal price) {
        this.price = price;
    }
}
