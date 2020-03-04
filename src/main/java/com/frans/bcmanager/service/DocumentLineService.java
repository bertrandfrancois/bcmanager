package com.frans.bcmanager.service;

import com.frans.bcmanager.model.DocumentLine;
import com.frans.bcmanager.repository.DocumentLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DocumentLineService implements BaseService<DocumentLine> {

    private final DocumentLineRepository documentLineRepository;

    @Autowired
    public DocumentLineService(DocumentLineRepository documentLineRepository) {
        this.documentLineRepository = documentLineRepository;
    }

    @Override
    public List<DocumentLine> findAll() {
        return null;
    }

    @Override
    public DocumentLine save(DocumentLine param) {
        return documentLineRepository.save(param);
    }

    @Override
    public DocumentLine find(long id) {
        return null;
    }

    @Override
    public void delete(long id) {
        documentLineRepository.deleteById(id);
    }
}
