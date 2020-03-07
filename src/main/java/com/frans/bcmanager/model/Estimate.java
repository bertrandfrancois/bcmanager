package com.frans.bcmanager.model;

import com.frans.bcmanager.validation.EstimateCode;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("ESTIMATE")
@Data
public class Estimate extends Document {

    @EstimateCode
    @Column(name = "DOCUMENT_CODE")
    private String code;

    @Override
    public LocalDate getPaymentDate() {
        return null;
    }

    @Override
    public Project getProject() {
        return null;
    }
}
