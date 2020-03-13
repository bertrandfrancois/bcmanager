package com.frans.bcmanager.model;

import com.frans.bcmanager.validation.EstimateCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.List;

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
}
