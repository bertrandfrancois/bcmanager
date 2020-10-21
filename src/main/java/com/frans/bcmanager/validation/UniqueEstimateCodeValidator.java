package com.frans.bcmanager.validation;

import com.frans.bcmanager.model.Document;
import com.frans.bcmanager.repository.DocumentRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEstimateCodeValidator implements ConstraintValidator<UniqueEstimateCode, Document> {

    private DocumentRepository documentRepository;

    public UniqueEstimateCodeValidator(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public void initialize(UniqueEstimateCode constraintAnnotation) {

    }

    @Override
    public boolean isValid(Document document, ConstraintValidatorContext context) {
        return documentRepository
                .findEstimateByCode(document.getCode())
                .stream()
                .allMatch(d -> d.getId().equals(document.getId()));
    }
}
