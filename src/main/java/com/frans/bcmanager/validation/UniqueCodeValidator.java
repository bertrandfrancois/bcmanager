package com.frans.bcmanager.validation;

import com.frans.bcmanager.model.Document;
import com.frans.bcmanager.repository.DocumentRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueCodeValidator implements ConstraintValidator<UniqueCode, Document> {

    private DocumentRepository documentRepository;

    public UniqueCodeValidator(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public void initialize(UniqueCode constraintAnnotation) {

    }

    @Override
    public boolean isValid(Document document, ConstraintValidatorContext context) {
        return documentRepository
                .findDocumentsByCode(document.getCode())
                .stream()
                .allMatch(d -> d.getId().equals(document.getId()));
    }
}
