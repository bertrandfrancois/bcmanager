package com.frans.bcmanager.model;

import com.frans.bcmanager.validation.EstimateCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("ESTIMATE")
@NoArgsConstructor
@Data
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
    @EstimateCode
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
    public boolean canBeEdited() {
        return getStatus() == DocumentStatus.NOT_ACCEPTED && getLinkedDocument() == null;
    }

    public boolean canBeConverted() {
        return getStatus() == DocumentStatus.ACCEPTED && getLinkedDocument() == null;
    }
}
