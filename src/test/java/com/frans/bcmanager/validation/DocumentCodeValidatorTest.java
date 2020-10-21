package com.frans.bcmanager.validation;

import com.frans.bcmanager.config.MockitoTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;

public class DocumentCodeValidatorTest extends MockitoTest {

    @InjectMocks
    private DocumentCodeValidator documentCodeValidator;

    @Mock
    private ConstraintValidatorContext context;

    @Test
    public void isValid() {
        assertThat(documentCodeValidator.isValid("1234567", context)).isTrue();
    }

    @Test
    public void isValid_Length8_ReturnsFalse() {
        assertThat(documentCodeValidator.isValid("12345678", context)).isFalse();
    }

    @Test
    public void isValid_Length6_ReturnsFalse() {
        assertThat(documentCodeValidator.isValid("123456", context)).isFalse();
    }

    @Test
    public void isValid_NotOnlyDigits_ReturnsFalse() {
        assertThat(documentCodeValidator.isValid("123b567", context)).isFalse();
    }

}
