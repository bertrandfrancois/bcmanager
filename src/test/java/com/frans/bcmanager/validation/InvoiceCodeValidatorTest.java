package com.frans.bcmanager.validation;

import com.frans.bcmanager.config.MockitoTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;

public class InvoiceCodeValidatorTest extends MockitoTest {

    @InjectMocks
    private InvoiceCodeValidator invoiceCodeValidator;

    @Mock
    private ConstraintValidatorContext context;

    @Test
    public void isValid() {
        assertThat(invoiceCodeValidator.isValid("F1234567", context)).isTrue();
    }

    @Test
    public void isValid_Length9_ReturnsFalse() {
        assertThat(invoiceCodeValidator.isValid("F12345678", context)).isFalse();
    }

    @Test
    public void isValid_Length7_ReturnsFalse() {
        assertThat(invoiceCodeValidator.isValid("F123456", context)).isFalse();
    }

    @Test
    public void isValid_NotOnlyDigits_ReturnsFalse() {
        assertThat(invoiceCodeValidator.isValid("F123b567", context)).isFalse();
    }

    @Test
    public void isValid_DoesNotStartWithF_ReturnsFalse() {
        assertThat(invoiceCodeValidator.isValid("C1234567", context)).isFalse();
    }
}