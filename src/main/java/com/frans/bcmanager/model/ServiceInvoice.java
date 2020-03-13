package com.frans.bcmanager.model;

import com.frans.bcmanager.enums.TaxRate;
import com.frans.bcmanager.validation.InvoiceCode;
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
public class ServiceInvoice extends Document {

    @Column(name = "PAYMENT_DATE")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;

    @Builder
    public ServiceInvoice(String code,
                          LocalDate creationDate,
                          LocalDate paymentDate,
                          TaxRate taxRate,
                          DocumentStatus status,
                          Client client,
                          List<DocumentLine> documentLines,
                          Document linkedDocument) {
        super(code, creationDate, taxRate, status, client, documentLines, linkedDocument);
        this.paymentDate = paymentDate;
    }

    @Override
    @InvoiceCode
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
}
