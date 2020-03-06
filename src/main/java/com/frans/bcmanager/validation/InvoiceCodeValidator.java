package com.frans.bcmanager.validation;

import org.apache.commons.lang3.math.NumberUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InvoiceCodeValidator implements ConstraintValidator<InvoiceCode, String> {

    @Override
    public void initialize(InvoiceCode constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.length() == 8
               && value.substring(0, 1).equals("F")
               && NumberUtils.isDigits(value.substring(1));
    }
}
