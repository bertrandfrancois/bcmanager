package com.frans.bcmanager.model;

import com.frans.bcmanager.validation.EstimateCode;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ESTIMATE")
@Data
public class Estimate extends Document {

    @EstimateCode
    @Column(name = "DOCUMENT_CODE")
    private String code;

}
