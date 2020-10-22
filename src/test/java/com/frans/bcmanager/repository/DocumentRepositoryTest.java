package com.frans.bcmanager.repository;

import com.frans.bcmanager.enums.TaxRate;
import com.frans.bcmanager.model.DocumentStatus;
import com.frans.bcmanager.model.Estimate;
import com.frans.bcmanager.model.ProjectInvoice;
import com.frans.bcmanager.model.ServiceInvoice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DocumentRepositoryTest {

    @Autowired
    private DocumentRepository repository;

    @Test
    void findInvoiceByCode() {
        ProjectInvoice projectInvoice = ProjectInvoice.builder()
                                                      .creationDate(now())
                                                      .paymentDate(now().plusDays(7))
                                                      .code("2020789")
                                                      .taxRate(TaxRate.T21)
                                                      .build();

        ServiceInvoice serviceInvoice = ServiceInvoice.builder()
                                                      .creationDate(now())
                                                      .paymentDate(now().plusDays(7))
                                                      .code("2020456")
                                                      .taxRate(TaxRate.T21)
                                                      .build();

        repository.save(projectInvoice);
        repository.save(serviceInvoice);

        assertThat(repository.findInvoiceByCode("2020789")).containsExactly(projectInvoice);
        assertThat(repository.findInvoiceByCode("2020456")).containsExactly(serviceInvoice);
        assertThat(repository.findInvoiceByCode("2020001")).isEmpty();
    }

    @Test
    void findEstimateByCode() {
        Estimate estimate = Estimate.builder()
                                    .code("2020001")
                                    .creationDate(now())
                                    .taxRate(TaxRate.T21)
                                    .build();
        repository.save(estimate);

        assertThat(repository.findEstimateByCode("2020001")).containsExactly(estimate);
        assertThat(repository.findEstimateByCode("2020002")).isEmpty();
    }

    @Test
    void findDocumentByStructuredCommunication() {
        ProjectInvoice projectInvoice = ProjectInvoice.builder()
                                                      .creationDate(now())
                                                      .paymentDate(now().plusDays(7))
                                                      .code("2020789")
                                                      .taxRate(TaxRate.T21)
                                                      .structuredCommunication("741672807315")
                                                      .build();

        ServiceInvoice serviceInvoice = ServiceInvoice.builder()
                                                      .creationDate(now())
                                                      .paymentDate(now().plusDays(7))
                                                      .code("2020456")
                                                      .taxRate(TaxRate.T21)
                                                      .structuredCommunication("226463376481")
                                                      .build();

        repository.save(projectInvoice);
        repository.save(serviceInvoice);

        assertThat(repository.findDocumentByStructuredCommunication("741672807315")).containsExactly(projectInvoice);
        assertThat(repository.findDocumentByStructuredCommunication("226463376481")).containsExactly(serviceInvoice);
    }

    @Test
    void getLastInvoiceCode() {
        ProjectInvoice projectInvoice = ProjectInvoice.builder()
                                                      .creationDate(now())
                                                      .paymentDate(now().plusDays(7))
                                                      .code("2020789")
                                                      .taxRate(TaxRate.T21)
                                                      .structuredCommunication("741672807315")
                                                      .build();

        repository.save(projectInvoice);

        assertThat(repository.getLastInvoiceCode()).isEqualTo(Optional.of("2020789"));
    }

    @Test
    void getLastEstimateCode() {
        Estimate estimate = Estimate.builder()
                                    .code("2020124")
                                    .creationDate(now())
                                    .taxRate(TaxRate.T21)
                                    .build();

        repository.save(estimate);

        assertThat(repository.getLastEstimateCode()).isEqualTo(Optional.of("2020124"));
    }

    @Test
    void findUnPaidDocuments() {
        ProjectInvoice projectInvoice = ProjectInvoice.builder()
                                                      .creationDate(now())
                                                      .paymentDate(now().minusDays(1))
                                                      .code("2020789")
                                                      .taxRate(TaxRate.T21)
                                                      .structuredCommunication("741672807315")
                                                      .status(DocumentStatus.NOT_PAID)
                                                      .build();

        ProjectInvoice projectInvoice2 = ProjectInvoice.builder()
                                                      .creationDate(now())
                                                      .paymentDate(now())
                                                      .code("2020790")
                                                      .taxRate(TaxRate.T21)
                                                      .status(DocumentStatus.NOT_PAID)
                                                      .structuredCommunication("741672807315")
                                                      .build();

        ProjectInvoice projectInvoice3 = ProjectInvoice.builder()
                                                      .creationDate(now())
                                                      .paymentDate(now().minusDays(1))
                                                      .code("2020790")
                                                      .taxRate(TaxRate.T21)
                                                      .status(DocumentStatus.PAID)
                                                      .structuredCommunication("741672807315")
                                                      .build();

        repository.save(projectInvoice);
        repository.save(projectInvoice2);
        repository.save(projectInvoice3);

        assertThat(repository.findUnPaidDocuments()).containsExactly(projectInvoice);

    }

    @Test
    void findAllInvoices() {
        ProjectInvoice projectInvoice = ProjectInvoice.builder()
                                                      .creationDate(now())
                                                      .paymentDate(now().plusDays(7))
                                                      .code("2020789")
                                                      .taxRate(TaxRate.T21)
                                                      .build();

        ServiceInvoice serviceInvoice = ServiceInvoice.builder()
                                                      .creationDate(now())
                                                      .paymentDate(now().plusDays(7))
                                                      .code("2020456")
                                                      .taxRate(TaxRate.T21)
                                                      .build();

        Estimate estimate = Estimate.builder()
                                    .code("2020124")
                                    .creationDate(now())
                                    .taxRate(TaxRate.T21)
                                    .build();

        repository.save(projectInvoice);
        repository.save(serviceInvoice);
        repository.save(estimate);

        assertThat(repository.findAllInvoices()).containsOnly(projectInvoice, serviceInvoice);

    }

    @Test
    void findAllEstimates() {
        ProjectInvoice projectInvoice = ProjectInvoice.builder()
                                                      .creationDate(now())
                                                      .paymentDate(now().plusDays(7))
                                                      .code("2020789")
                                                      .taxRate(TaxRate.T21)
                                                      .build();

        ServiceInvoice serviceInvoice = ServiceInvoice.builder()
                                                      .creationDate(now())
                                                      .paymentDate(now().plusDays(7))
                                                      .code("2020456")
                                                      .taxRate(TaxRate.T21)
                                                      .build();

        Estimate estimate = Estimate.builder()
                                    .code("2020124")
                                    .creationDate(now())
                                    .taxRate(TaxRate.T21)
                                    .build();

        repository.save(projectInvoice);
        repository.save(serviceInvoice);
        repository.save(estimate);

        assertThat(repository.findAllEstimates()).containsOnly(estimate);
    }
}
