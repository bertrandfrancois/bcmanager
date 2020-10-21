package com.frans.bcmanager.validation;

import org.apache.commons.lang3.math.NumberUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DocumentCodeValidator implements ConstraintValidator<DocumentCode, String> {

    @Override
    public void initialize(DocumentCode constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.length() == 7
               && NumberUtils.isDigits(value);
    }
}
