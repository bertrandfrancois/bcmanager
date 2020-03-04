package com.frans.bcmanager.validation;

import org.apache.commons.lang3.math.NumberUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CodePatternValidator implements ConstraintValidator<ValidCode, String> {

    @Override
    public void initialize(ValidCode constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.length() == 7
               && NumberUtils.isDigits(value);
    }
}
