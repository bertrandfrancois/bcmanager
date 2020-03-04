package com.frans.bcmanager.validation;

import com.frans.bcmanager.config.MockitoTest;
import com.frans.bcmanager.model.Period;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class PeriodValidatorTest extends MockitoTest {

    @InjectMocks
    private PeriodValidator periodValidator;

    @Mock
    private Period period;

    @Mock
    private ConstraintValidatorContext constraint;
    @Test
    public void isValid() {

        given(period.getStartDate()).willReturn(LocalDate.of(2018, 1, 1));
        given(period.getEndDate()).willReturn(LocalDate.of(2018, 1, 2));

        assertThat(periodValidator.isValid(period, constraint)).isTrue();
    }
    @Test
    public void isValid_SameDate() {

        given(period.getStartDate()).willReturn(LocalDate.of(2018, 1, 1));
        given(period.getEndDate()).willReturn(LocalDate.of(2018, 1, 1));

        assertThat(periodValidator.isValid(period, constraint)).isTrue();
    }
    @Test
    public void isValid_EndDateBeforeStartDate_ReturnsFalse() {

        given(period.getStartDate()).willReturn(LocalDate.of(2018, 1, 2));
        given(period.getEndDate()).willReturn(LocalDate.of(2018, 1, 1));

        assertThat(periodValidator.isValid(period, constraint)).isFalse();
    }
}