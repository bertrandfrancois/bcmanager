package com.frans.bcmanager.service;

import com.frans.bcmanager.model.ConversionDTO;
import com.frans.bcmanager.model.Document;
import com.frans.bcmanager.model.DocumentLine;
import com.frans.bcmanager.model.Estimate;
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
    private final ConvertEstimateToInvoiceService convertEstimateToInvoiceService;

    @Autowired
    public DocumentService(DocumentRepository documentRepository,
                           ConvertEstimateToInvoiceService convertEstimateToInvoiceService) {
        this.documentRepository = documentRepository;
        this.convertEstimateToInvoiceService = convertEstimateToInvoiceService;
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

    public List<Document> getUnpaidDocuments() {
        return documentRepository.findUnPaidDocuments();
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

    public Estimate copyEstimate(long documentId) {
        Optional<Document> document = documentRepository.findById(documentId);
        Estimate copy = ((Estimate) document.get().clone());
        copy.setCode(getNextEstimateCode());
        documentRepository.save(copy);
        return copy;
    }

    public Document copyInvoice(long documentId) {
        Optional<Document> document = documentRepository.findById(documentId);
        Document copy = ((Document) document.get().clone());
        copy.setCode(getNextInvoiceCode());
        documentRepository.save(copy);
        return copy;
    }

    public void addDocumentLine(Document document, DocumentLine documentLine) {
        document.addDocumentLine(documentLine);
    }

    public void editDocumentLine(Document document, DocumentLine documentLine) {
        document.editDocumentLine(documentLine);
    }

    public void deleteDocumentLine(Document document, long documentLineId) {
        document.deleteLine(documentLineId);
    }

    public void updateStatus(Document document) {
        document.setStatus(document.getStatus().getNextStatus());
    }

    public Document convertEstimateToInvoice(Estimate estimate, ConversionDTO conversionDTO) {
        Document invoice = convertEstimateToInvoiceService.convert(estimate, conversionDTO);
        this.save(invoice);
        estimate.setLinkedDocument(invoice);
        return invoice;
    }
}
