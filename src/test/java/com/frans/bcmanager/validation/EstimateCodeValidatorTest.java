package com.frans.bcmanager.validation;

import com.frans.bcmanager.config.MockitoTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;

public class EstimateCodeValidatorTest extends MockitoTest {

    @InjectMocks
    private EstimateCodeValidator estimateCodeValidator;

    @Mock
    private ConstraintValidatorContext context;

    @Test
    public void isValid() {
        assertThat(estimateCodeValidator.isValid("D1234567", context)).isTrue();
    }

    @Test
    public void isValid_Length9_ReturnsFalse() {
        assertThat(estimateCodeValidator.isValid("D12345678", context)).isFalse();
    }

    @Test
    public void isValid_Length7_ReturnsFalse() {
        assertThat(estimateCodeValidator.isValid("D123456", context)).isFalse();
    }

    @Test
    public void isValid_NotOnlyDigits_ReturnsFalse() {
        assertThat(estimateCodeValidator.isValid("D123b567", context)).isFalse();
    }

    @Test
    public void isValid_DoesNotStartWithD_ReturnsFalse() {
        assertThat(estimateCodeValidator.isValid("C1234567", context)).isFalse();
    }
}