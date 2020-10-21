package com.frans.bcmanager.validation;

import com.frans.bcmanager.model.Document;
import com.frans.bcmanager.repository.DocumentRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueInvoiceCodeValidator implements ConstraintValidator<UniqueInvoiceCode, Document> {

    private DocumentRepository documentRepository;

    public UniqueInvoiceCodeValidator(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public void initialize(UniqueInvoiceCode constraintAnnotation) {

    }

    @Override
    public boolean isValid(Document document, ConstraintValidatorContext context) {
        return documentRepository
                .findInvoiceByCode(document.getCode())
                .stream()
                .allMatch(d -> d.getId().equals(document.getId()));
    }
}
