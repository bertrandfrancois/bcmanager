package com.frans.bcmanager.repository;

import com.frans.bcmanager.model.Document;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DocumentRepository extends CrudRepository<Document, Long> {

    @Query(value = "SELECT * FROM DOCUMENTS d WHERE d.document_code = ?1",
           nativeQuery = true)
    List<Document> findDocumentsByCode(String value);
}
