package com.frans.bcmanager.repository;

import com.frans.bcmanager.model.Document;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends CrudRepository<Document, Long> {

    Optional<Document> findByCode(String value);
    List<Document> findDocumentsByCode(String value);
}
