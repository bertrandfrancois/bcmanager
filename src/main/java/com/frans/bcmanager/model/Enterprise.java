package com.frans.bcmanager.model;

import com.frans.bcmanager.validation.TaxNumber;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity(name = "ENTERPRISE")
@NoArgsConstructor
@Data
public class Enterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotEmpty
    @Size(max = 30)
    @Column(name = "NAME")
    private String name;

    @Valid
    @Embedded
    private Address address;

    @NotEmpty
    @Size(max = 30)
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Email
    @Size(max = 60)
    @Column(name = "MAIL")
    private String mail;

    @NotEmpty
    @Size(max = 30)
    @TaxNumber
    @Column(name = "TAX_NUMBER")
    private String taxNumber;

    @NotEmpty
    @Size(max = 30)
    @Column(name = "BIC")
    private String bicNumber;

    @NotEmpty
    @Size(max = 30)
    @Column(name = "IBAN")
    private String ibanNumber;

    @Column(name = "REGISTER_DATE")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registerDate;
}
