package com.frans.bcmanager.validation;

import com.frans.bcmanager.config.MockitoTest;
import com.frans.bcmanager.model.Document;
import com.frans.bcmanager.repository.DocumentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.validation.ConstraintValidatorContext;
import java.util.List;

import static org.mockito.Mockito.when;

public class UniqueCodeValidatorTest extends MockitoTest {

    private static final String CODE = "CODE";
    private static final long DOCUMENT_ID = 1L;
    private static final long OTHER_DOCUMENT_ID = 2L;

    @InjectMocks
    private UniqueCodeValidator uniqueCodeValidator;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private Document document, existingDocument;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        when(document.getId()).thenReturn(DOCUMENT_ID);
        when(document.getCode()).thenReturn(CODE);
        when(documentRepository.findDocumentsByCode(CODE)).thenReturn(List.of());
    }
    @Test
    public void isValid() {
        Assertions.assertThat(uniqueCodeValidator.isValid(document, context)).isTrue();
    }
    @Test
    public void isValid_DocumentCodeExist_SameDocumentId_ReturnsTrue() {
        when(documentRepository.findDocumentsByCode(CODE)).thenReturn(List.of(existingDocument));
        when(existingDocument.getId()).thenReturn(DOCUMENT_ID);

        Assertions.assertThat(uniqueCodeValidator.isValid(document, context)).isTrue();
    }
    @Test
    public void isValid_DocumentCodeExist_OtherDocumentId_ReturnsFalse() {
        when(documentRepository.findDocumentsByCode(CODE)).thenReturn(List.of(existingDocument));
        when(existingDocument.getId()).thenReturn(OTHER_DOCUMENT_ID);

        Assertions.assertThat(uniqueCodeValidator.isValid(document, context)).isFalse();
    }
    @Test
    public void isValid_DocumentCodeExist_NewDocument_ReturnsFalse() {
        when(document.getId()).thenReturn(null);
        when(documentRepository.findDocumentsByCode(CODE)).thenReturn(List.of(existingDocument));

        Assertions.assertThat(uniqueCodeValidator.isValid(document, context)).isFalse();
    }
}
