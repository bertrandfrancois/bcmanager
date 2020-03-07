package com.frans.bcmanager.service;

import com.frans.bcmanager.model.Document;
import com.frans.bcmanager.repository.DocumentRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DocumentService implements BaseService<Document> {

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public List<Document> findAll() {
        return Lists.newArrayList(documentRepository.findAll());
    }

    @Override
    public Document save(Document document) {
        return documentRepository.save(document);
    }

    @Override
    public Document find(long id) {
        return documentRepository.findById(id).get();
    }

    @Override
    public void delete(long id) {
        documentRepository.deleteById(id);
    }

    public List<Document> findTop5() {
        return documentRepository.findTop5ByOrderByIdDesc();
    }

    public String getNextEstimateCode() {
        Optional<String> lastEstimateCode = documentRepository.getLastEstimateCode();
        if (lastEstimateCode.isPresent()) {
            long nextNumber = Long.parseLong(lastEstimateCode.get().substring(1)) + 1;
            return "D" + nextNumber;
        }
        return "";
    }

    public String getNextInvoiceCode() {
        Optional<String> lastInvoiceCode = documentRepository.getLastInvoiceCode();
        if (lastInvoiceCode.isPresent()) {
            long nextNumber = Long.parseLong(lastInvoiceCode.get().substring(1)) + 1;
            return "F" + nextNumber;
        }
        return "";
    }
}
