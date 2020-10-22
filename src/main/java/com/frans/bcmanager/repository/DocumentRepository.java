package com.frans.bcmanager.repository;

import com.frans.bcmanager.model.Document;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends CrudRepository<Document, Long> {

    @Query(value = "SELECT * FROM DOCUMENTS d WHERE type in ('SERVICE_INVOICE', 'PROJECT_INVOICE') and d.document_code = ?1",
           nativeQuery = true)
    List<Document> findInvoiceByCode(String value);

    @Query(value = "SELECT * FROM DOCUMENTS d WHERE type = 'ESTIMATE' and d.document_code = ?1",
           nativeQuery = true)
    List<Document> findEstimateByCode(String value);

    @Query(value = "SELECT * FROM DOCUMENTS d WHERE d.structured_communication = ?1",
           nativeQuery = true)
    List<Document> findDocumentByStructuredCommunication(String structuredCommunication);

    @Query(value = "SELECT MAX(document_code) FROM DOCUMENTS d where type in ('SERVICE_INVOICE', 'PROJECT_INVOICE')",
           nativeQuery = true)
    Optional<String> getLastInvoiceCode();

    @Query(value = "SELECT MAX(document_code) FROM DOCUMENTS d WHERE type = 'ESTIMATE'",
           nativeQuery = true)
    Optional<String> getLastEstimateCode();

    @Query(value = "SELECT * FROM DOCUMENTS d WHERE status='NOT_PAID' and payment_date < now()\\:\\:date",
           nativeQuery = true)
    List<Document> findUnPaidDocuments();

    @Query(value = "SELECT * FROM DOCUMENTS d WHERE type in ('SERVICE_INVOICE', 'PROJECT_INVOICE')" +
                   " ORDER BY document_code DESC",
           nativeQuery = true)
    List<Document> findAllInvoices();

    @Query(value = "SELECT * FROM DOCUMENTS d WHERE type = 'ESTIMATE'" +
                   " ORDER BY document_code DESC",
           nativeQuery = true)
    List<Document> findAllEstimates();
}
