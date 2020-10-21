package com.frans.bcmanager.validation;

import org.apache.commons.lang3.math.NumberUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

public class TaxNumberValidator implements ConstraintValidator<TaxNumber, String> {

    @Override
    public void initialize(TaxNumber constraintAnnotation) {

    }

    @Override
    public boolean isValid(String taxNumber, ConstraintValidatorContext context) {
        return taxNumber.isEmpty() ||
               (NumberUtils.isDigits(taxNumber)
                && taxNumber.length() == 10 && hasValidCheckNumber(taxNumber));
    }

    private boolean hasValidCheckNumber(String taxNumber) {
        String base = taxNumber.substring(0, 8);
        String checkNumber = taxNumber.substring(8);
        int modulo = parseInt(base) % 97;

        return parseInt(checkNumber) == (97 - modulo);
    }
}
