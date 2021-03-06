package com.frans.bcmanager.model;

import com.frans.bcmanager.enums.TaxRate;
import com.google.common.collect.Lists;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

@Entity(name = "DOCUMENTS")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE")
@Data
@NoArgsConstructor
public abstract class Document implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DOCUMENT_ID")
    private Long id;

    @Column(name = "DOCUMENT_CODE")
    private String code;

    @Column(name = "CREATION_DATE")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @Column(name = "TAX_RATE")
    @Enumerated(EnumType.STRING)
    private TaxRate taxRate;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "DOCUMENT_ID")
    protected List<DocumentLine> documentLines = List.of();

    @OneToMany
    @JoinTable(
            name = "LINKED_DOCUMENTS",
            joinColumns = @JoinColumn(name = "DOCUMENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "LINKED_DOCUMENT_ID")
    )
    private List<Document> linkedDocuments;

    public Document(String code,
                    LocalDate creationDate,
                    TaxRate taxRate,
                    DocumentStatus status,
                    Client client,
                    List<DocumentLine> documentLines,
                    List<Document> linkedDocuments) {

        this.code = code;
        this.creationDate = creationDate;
        this.taxRate = taxRate;
        this.status = status;
        this.client = client;
        this.documentLines = documentLines;
        this.linkedDocuments = linkedDocuments;
    }

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
        return getSubTotal().add(getSubTotal().multiply(taxRate.getValue())).setScale(2, RoundingMode.HALF_DOWN);
    }

    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    public BigDecimal getTotalTax() {
        return getSubTotal().multiply(taxRate.getValue()).setScale(2, RoundingMode.HALF_DOWN);
    }

    public abstract LocalDate getPaymentDate();

    public abstract Project getProject();

    public abstract String getLink();

    public abstract String getStructuredCommunication();

    public abstract void setStructuredCommunication(String structuredCommunication);

    public abstract boolean canBeEdited();

    public List<DocumentLine> getDocumentLines() {
        return documentLines.stream()
                            .sorted(Comparator.comparing(DocumentLine::getId))
                            .collect(Collectors.toList());
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

    @Override
    public Object clone() {
        try {
            List<DocumentLine> lines = Lists.newArrayList();
            Document clone = (Document) super.clone();
            clone.setId(null);
            clone.setCode(null);
            for (DocumentLine documentLine : documentLines) {
                lines.add((DocumentLine) documentLine.clone());
            }
            clone.setDocumentLines(lines);
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
