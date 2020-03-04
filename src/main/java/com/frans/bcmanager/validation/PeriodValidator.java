package com.frans.bcmanager.validation;


import com.frans.bcmanager.model.Period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PeriodValidator implements ConstraintValidator<ValidPeriod, Period> {

    @Override
    public void initialize(ValidPeriod constraintAnnotation) {

    }

    @Override
    public boolean isValid(Period value, ConstraintValidatorContext context) {
        return value.getStartDate().isBefore(value.getEndDate())
               || value.getStartDate().isEqual(value.getEndDate());
    }
}
