package com.frans.bcmanager.model;

import com.frans.bcmanager.enums.TaxRate;
import com.frans.bcmanager.format.StructuredCommunication;
import com.frans.bcmanager.validation.DocumentCode;
import com.frans.bcmanager.validation.UniqueInvoiceCode;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue("PROJECT_INVOICE")
@Data
@NoArgsConstructor
@UniqueInvoiceCode
public class ProjectInvoice extends Document {

    private ProjectInvoice(String structuredCommunication) {
        this.structuredCommunication = structuredCommunication;
    }

    @Builder
    public ProjectInvoice(String code,
                          LocalDate creationDate,
                          LocalDate paymentDate,
                          TaxRate taxRate,
                          DocumentStatus status,
                          String structuredCommunication,
                          Client client,
                          Project project,
                          List<DocumentLine> documentLines,
                          Document linkedDocument) {
        super(code, creationDate, taxRate, status, client, documentLines, linkedDocument);
        this.paymentDate = paymentDate;
        this.structuredCommunication = structuredCommunication;
        this.project = project;
    }

    @Column(name = "PAYMENT_DATE")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

    @Column(name = "STRUCTURED_COMMUNICATION")
    @NotNull
    @StructuredCommunication
    private String structuredCommunication;

    @Override
    @DocumentCode
    public String getCode() {
        return super.getCode();
    }

    @Override
    public String getLink() {
        return "/clients/" + getClient().getId() + "/projects/" + getProject().getId() + "/documents/" + getId();
    }

    @Override
    public boolean canBeEdited() {
        return getStatus() == DocumentStatus.NOT_PAID;
    }

    public String getStructuredCommunication() {
        return structuredCommunication;
    }
}
