package com.frans.bcmanager.model;

import com.frans.bcmanager.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity(name = "DOCUMENT_LINES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DOCUMENT_LINE_ID")
    private Long id;

    @Column(name = "DESCRIPTION")
    @NotEmpty
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Unit unit;

    @Digits(integer = 12, fraction = 3)
    @NotNull
    private BigDecimal quantity;

    @Digits(integer = 12, fraction = 2)
    @NotNull
    private BigDecimal price;

    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    public BigDecimal getTotal() {
        return price.multiply(quantity);
    }

    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    public BigDecimal getFormattedPrice() {
        return price;
    }

    public void update(DocumentLine documentLine) {
        this.description = documentLine.description;
        this.price = documentLine.price;
        this.quantity = documentLine.quantity;
        this.unit = documentLine.unit;
    }
}
