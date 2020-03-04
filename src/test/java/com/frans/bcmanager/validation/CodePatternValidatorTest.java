package com.frans.bcmanager.validation;

import com.frans.bcmanager.config.MockitoTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;

public class CodePatternValidatorTest extends MockitoTest {

    @InjectMocks
    private CodePatternValidator codePatternValidator;

    @Mock
    private ConstraintValidatorContext context;
    @Test
    public void isValid() {
        assertThat(codePatternValidator.isValid("1234567", context)).isTrue();
    }
    @Test
    public void isValid_8Digits_ReturnsFalse() {
        assertThat(codePatternValidator.isValid("12345678", context)).isFalse();
    }
    @Test
    public void isValid_6Digits_ReturnsFalse() {
        assertThat(codePatternValidator.isValid("123456", context)).isFalse();
    }
    @Test
    public void isValid_NotOnlyDigits_ReturnsFalse() {
        assertThat(codePatternValidator.isValid("123b5678", context)).isFalse();
    }
}