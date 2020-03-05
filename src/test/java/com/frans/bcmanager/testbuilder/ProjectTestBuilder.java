package com.frans.bcmanager.testbuilder;

import com.frans.bcmanager.model.Address;
import com.frans.bcmanager.model.Client;
import com.frans.bcmanager.model.Period;
import com.frans.bcmanager.model.Project;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

public class ProjectTestBuilder {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private Long id = null;
    private String description = "description";
    private Address address = new Address("street", "12345", "city");
    private Client client;

    public ProjectTestBuilder() {
    }

    public static ProjectTestBuilder project() {
        return new ProjectTestBuilder();
    }

    public Project build() {
        return new Project(id,
                           List.of(),
                           description,
                           address,
                           client);
    }

    public ProjectTestBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public ProjectTestBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ProjectTestBuilder withAddress(Address address) {
        this.address = address;
        return this;
    }

    //    public ProjectTestBuilder withStartDate(String startDate) {
    //        this.startDate = date(startDate);
    //        return this;
    //    }
    //
    //    public ProjectTestBuilder withEndDate(String endDate) {
    //        this.endDate = date(endDate);
    //        return this;
    //    }

    public ProjectTestBuilder withClient(Client client) {
        this.client = client;
        return this;
    }
}
