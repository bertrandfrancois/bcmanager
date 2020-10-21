package com.frans.bcmanager.service;

import com.frans.bcmanager.repository.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class StructuredCommunicationFactoryTest {

    @InjectMocks
    private StructuredCommunicationFactory structuredCommunicationFactory;

    @Mock
    private DocumentRepository documentRepository;

    @Test
    void create() {
        for (int i = 0; i < 100; i++) {
            String structuredCommunication = structuredCommunicationFactory.create();
            String firstPart = structuredCommunication.substring(0, 10);
            String controlNumber = structuredCommunication.substring(10);
            long calculatedControlNumber = Long.parseLong(firstPart) % 97;
            if (controlNumber.equals("97")) {
                assertThat(calculatedControlNumber).isEqualTo(0L);
            } else {
                assertThat(calculatedControlNumber).isEqualTo(Long.valueOf(controlNumber));
            }
        }
    }
}
