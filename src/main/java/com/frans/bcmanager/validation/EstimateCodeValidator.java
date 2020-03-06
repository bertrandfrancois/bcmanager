package com.frans.bcmanager.validation;

import org.apache.commons.lang3.math.NumberUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EstimateCodeValidator implements ConstraintValidator<EstimateCode, String> {

    @Override
    public void initialize(EstimateCode constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.length() == 8
               && value.substring(0, 1).equals("D")
               && NumberUtils.isDigits(value.substring(1));
    }
}
