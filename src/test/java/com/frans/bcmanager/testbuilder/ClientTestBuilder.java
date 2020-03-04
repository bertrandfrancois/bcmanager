package com.frans.bcmanager.testbuilder;

import com.frans.bcmanager.model.Address;
import com.frans.bcmanager.model.Client;
import com.frans.bcmanager.model.Document;
import com.frans.bcmanager.model.Project;

import java.util.List;

public class ClientTestBuilder {

    private String lastName = "Doe";

    private String firstName = "John";

    private Address address = new Address("street", "12345", "city");

    private String phoneNumber = "phoneNumber";

    private String mail = "mail@mail.com";

    private String taxNumber = "taxNumber";

    private List<Project> projects = List.of();

    private List<Document> documents = List.of();

    private Long id = null;

    public static ClientTestBuilder client() {
        return new ClientTestBuilder();
    }

    public Client build() {
        return new Client(id, lastName, firstName, address, phoneNumber, mail, taxNumber, projects, documents);
    }

    public ClientTestBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ClientTestBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ClientTestBuilder withAddress(Address address) {
        this.address = address;
        return this;
    }

    public ClientTestBuilder withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public ClientTestBuilder withMail(String mail) {
        this.mail = mail;
        return this;
    }

    public ClientTestBuilder withTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
        return this;
    }

    public ClientTestBuilder withProjects(List<Project> projects) {
        this.projects = projects;
        return this;
    }

    public ClientTestBuilder withDocuments(List<Document> documents) {
        this.documents = documents;
        return this;
    }

    public ClientTestBuilder withId(Long id) {
        this.id = id;
        return this;
    }
}
