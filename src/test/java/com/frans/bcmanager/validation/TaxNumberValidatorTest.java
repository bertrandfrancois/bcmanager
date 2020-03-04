package com.frans.bcmanager.validation;

import com.frans.bcmanager.config.MockitoTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;

public class TaxNumberValidatorTest extends MockitoTest {

    @InjectMocks
    private TaxNumberValidator taxNumberValidator;

    @Mock
    private ConstraintValidatorContext context;
    @Test
    public void isValid_NoTaxNumber_ReturnsTrue() {
        assertThat(taxNumberValidator.isValid("", context)).isTrue();
    }
    @Test
    public void isValid_ValidTaxNumber_ReturnsTrue() {
        assertThat(taxNumberValidator.isValid("0600834826", context)).isTrue();
    }
    @Test
    public void isValid_DoesNotCoontainsOnlyDigits_ReturnsFalse() {
        assertThat(taxNumberValidator.isValid("123456789b", context)).isFalse();
    }
    @Test
    public void isValid_MoreThan10DigitsTaxNumber_ReturnsFalse() {
        assertThat(taxNumberValidator.isValid("12345678901", context)).isFalse();
    }
    @Test
    public void isValid_LessThan10DigitsTaxNumber_ReturnsFalse() {
        assertThat(taxNumberValidator.isValid("123456789", context)).isFalse();
    }
}