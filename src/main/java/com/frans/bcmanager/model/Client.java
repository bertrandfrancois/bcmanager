package com.frans.bcmanager.model;

import com.frans.bcmanager.validation.TaxNumber;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "CLIENTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIENT_ID")
    private Long id;

    @NotEmpty
    @Size(max = 60)
    @Column(name = "LAST_NAME")
    private String lastName;

    @Size(max = 30)
    @Column(name = "FIRST_NAME")
    private String firstName;

    @Valid
    @Embedded
    private Address address;

    @NotEmpty
    @Size(max = 30)
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Email
    @Size(max = 30)
    @Column(name = "MAIL")
    private String mail;

    @Size(max = 30)
    @TaxNumber
    @Column(name = "TAX_NUMBER")
    private String taxNumber;

    @OneToMany(mappedBy = "client", cascade= CascadeType.ALL)
    private List<Project> projects = List.of();

    @OneToMany(mappedBy = "client", cascade= CascadeType.ALL)
    private List<Document> documents = List.of();

    public List<Estimate> getEstimates(){
        return documents
                .stream()
                .filter(Estimate.class::isInstance)
                .map(Estimate.class::cast)
                .collect(Collectors.toList());
    }

    public List<ServiceInvoice> getServices(){
        return documents
                .stream()
                .filter(ServiceInvoice.class::isInstance)
                .map(ServiceInvoice.class::cast)
                .collect(Collectors.toList());
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

}