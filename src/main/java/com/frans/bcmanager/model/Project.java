package com.frans.bcmanager.model;

 import com.google.common.collect.Lists;
 import lombok.AllArgsConstructor;
 import lombok.Data;
 import lombok.NoArgsConstructor;

 import javax.persistence.CascadeType;
 import javax.persistence.Column;
 import javax.persistence.Embedded;
import javax.persistence.Entity;
 import javax.persistence.FetchType;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
 import javax.persistence.OneToMany;
 import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
 import java.util.List;

@Entity(name="PROJECTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROJECT_ID")
    private Long id;

    @OneToMany(mappedBy = "project")
    private List<ProjectInvoice> documents = List.of();

    @NotEmpty
    @Column(name = "DESCRIPTION")
    private String description;

    @Valid
    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;
}
