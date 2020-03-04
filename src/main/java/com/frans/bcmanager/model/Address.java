package com.frans.bcmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @NotEmpty
    @Size(max = 50)
    @Column(name = "STREET")
    private String street;

    @NotEmpty
    @Size(max = 5)
    @Column(name = "POST_CODE")
    private String postCode;

    @NotEmpty
    @Size(max = 30)
    @Column(name = "CITY")
    private String city;
}