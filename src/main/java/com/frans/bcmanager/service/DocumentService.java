package com.frans.bcmanager.service;

import com.frans.bcmanager.model.Document;
import com.frans.bcmanager.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
        return null;
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

}
