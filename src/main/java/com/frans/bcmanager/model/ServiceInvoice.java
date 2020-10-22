package com.frans.bcmanager.model;

import com.frans.bcmanager.enums.TaxRate;
import com.frans.bcmanager.format.StructuredCommunication;
import com.frans.bcmanager.validation.DocumentCode;
import com.frans.bcmanager.validation.UniqueInvoiceCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue("SERVICE_INVOICE")
@Getter
@Setter
@NoArgsConstructor
@UniqueInvoiceCode
public class ServiceInvoice extends Document {

    @Column(name = "PAYMENT_DATE")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;

    @Column(name = "STRUCTURED_COMMUNICATION")
    @NotNull
    @StructuredCommunication
    private String structuredCommunication;

    private ServiceInvoice(String structuredCommunication) {
        this.structuredCommunication = structuredCommunication;
    }

    @Builder
    public ServiceInvoice(String code,
                          LocalDate creationDate,
                          LocalDate paymentDate,
                          TaxRate taxRate,
                          DocumentStatus status,
                          String structuredCommunication,
                          Client client,
                          List<DocumentLine> documentLines,
                          List<Document> linkedDocuments) {
        super(code, creationDate, taxRate, status, client, documentLines, linkedDocuments);
        this.paymentDate = paymentDate;
        this.structuredCommunication = structuredCommunication;
    }

    @Override
    @DocumentCode
    public String getCode() {
        return super.getCode();
    }

    @Override
    public Project getProject() {
        return null;
    }

    @Override
    public String getLink() {
        return "/clients/" + getClient().getId() + "/services/" + getId();
    }

    @Override
    public boolean canBeEdited() {
        return getStatus() == DocumentStatus.NOT_PAID;
    }

    public String getStructuredCommunication() {
        return structuredCommunication;
    }
}
