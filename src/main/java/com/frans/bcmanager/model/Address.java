package com.frans.bcmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Embeddable
@Getter
@Setter
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

    @Override
    public String toString() {
        return street + ", " + postCode + " " + city;
    }
}