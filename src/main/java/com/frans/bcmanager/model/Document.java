package com.frans.bcmanager.model;

import com.frans.bcmanager.enums.TaxRate;
import com.frans.bcmanager.validation.ClassCheck;
import com.frans.bcmanager.validation.UniqueCode;
import com.frans.bcmanager.validation.ValidCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

@Entity(name = "DOCUMENTS")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@UniqueCode(groups = ClassCheck.class)
@GroupSequence({Document.class, ClassCheck.class})
public abstract class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DOCUMENT_ID")
    private Long id;

    @ValidCode
    @Column(name = "DOCUMENT_CODE")
    private String code;

    @Column(name = "CREATION_DATE")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @Column(name = "TAX_RATE")
    @Enumerated(EnumType.STRING)
    private TaxRate taxRate;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "DOCUMENT_ID")
    protected List<DocumentLine> documentLines;

    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    public BigDecimal getSubTotal() {
        BigDecimal subTotal = BigDecimal.ZERO;
        for (DocumentLine documentLine : documentLines) {
            subTotal = subTotal.add(documentLine.getTotal());
        }
        return subTotal;
    }

    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    public BigDecimal getTotal() {
        return getSubTotal().add(getSubTotal().multiply(taxRate.getValue()));
    }

    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    public BigDecimal getTotalTax() {
        return getSubTotal().multiply(taxRate.getValue());
    }

    public void addDocumentLine(DocumentLine documentLine) {
        this.documentLines.add(documentLine);
    }

    public void editDocumentLine(DocumentLine documentLine) {
        DocumentLine currentDocumentLine = this.documentLines.stream()
                                                             .filter(dl -> dl.getId().equals(documentLine.getId()))
                                                             .findFirst()
                                                             .get();
        currentDocumentLine.update(documentLine);
    }

    public void deleteLine(long documentLineId) {
        this.documentLines.removeIf(dl -> dl.getId().equals(documentLineId));
    }
}
