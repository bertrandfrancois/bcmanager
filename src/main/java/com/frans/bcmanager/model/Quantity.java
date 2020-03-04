package com.frans.bcmanager.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Data
public class Quantity {

    @Column(name = "QUANTITY")
    private BigDecimal quantity;
}
