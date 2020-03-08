package com.frans.bcmanager.model;

import com.frans.bcmanager.validation.InvoiceCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("SERVICE_INVOICE")
@Data()
@NoArgsConstructor
public class ServiceInvoice extends Document {

    @Column(name = "PAYMENT_DATE")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;

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
