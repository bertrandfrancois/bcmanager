package com.frans.bcmanager.repository;

import com.frans.bcmanager.model.Document;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends CrudRepository<Document, Long> {

    @Query(value = "SELECT * FROM DOCUMENTS d WHERE d.document_code = ?1",
           nativeQuery = true)
    List<Document> findDocumentsByCode(String value);

    @Query(value = "SELECT MAX(document_code) FROM DOCUMENTS d WHERE d.document_code like 'F%'",
           nativeQuery = true)
    Optional<String> getLastInvoiceCode();

    @Query(value = "SELECT MAX(document_code) FROM DOCUMENTS d WHERE d.document_code like 'D%'",
           nativeQuery = true)
    Optional<String> getLastEstimateCode();
}
