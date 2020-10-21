package com.frans.bcmanager.model;

import com.frans.bcmanager.enums.TaxRate;
import com.frans.bcmanager.validation.DocumentCode;
import com.frans.bcmanager.validation.UniqueEstimateCode;
import com.frans.bcmanager.validation.UniqueInvoiceCode;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue("ESTIMATE")
@NoArgsConstructor
@Data
@UniqueEstimateCode
public class Estimate extends Document implements Cloneable {

    @Override
    public LocalDate getPaymentDate() {
        return null;
    }

    @Override
    public Project getProject() {
        return null;
    }

    @Override
    @DocumentCode
    public String getCode() {
        return super.getCode();
    }

    @Override
    public String getLink() {
        return "/clients/" + getClient().getId() + "/estimates/" + getId();
    }

    @Override
    public String getStructuredCommunication() {
        return null;
    }

    @Override
    public void setStructuredCommunication(String structuredCommunication) {
    }

    @Override
    public boolean canBeEdited() {
        return getStatus() == DocumentStatus.NOT_ACCEPTED && getLinkedDocument() == null;
    }

    public boolean canBeConverted() {
        return getStatus() == DocumentStatus.ACCEPTED && getLinkedDocument() == null;
    }

    @Builder
    public Estimate(String code,
                          LocalDate creationDate,
                          TaxRate taxRate,
                          DocumentStatus status,
                          Client client,
                          List<DocumentLine> documentLines,
                          Document linkedDocument) {
        super(code, creationDate, taxRate, status, client, documentLines, linkedDocument);
    }

}
