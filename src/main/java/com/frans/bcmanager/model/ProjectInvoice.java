package com.frans.bcmanager.model;

import com.frans.bcmanager.validation.InvoiceCode;
import lombok.AllArgsConstructor;
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

@Entity
@DiscriminatorValue("PROJECT_INVOICE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectInvoice extends Document {

    @InvoiceCode
    @Column(name = "DOCUMENT_CODE")
    private String code;

    @Column(name = "PAYMENT_DATE")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    private Project project;
}
