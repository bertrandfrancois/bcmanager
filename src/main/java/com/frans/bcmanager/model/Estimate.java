package com.frans.bcmanager.model;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ESTIMATE")
@Data
public class Estimate extends Document {

}
