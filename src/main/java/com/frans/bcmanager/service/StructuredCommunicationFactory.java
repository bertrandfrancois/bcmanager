package com.frans.bcmanager.service;

import com.frans.bcmanager.repository.DocumentRepository;
import org.springframework.stereotype.Component;

import java.util.Random;

import static java.lang.String.valueOf;
import static org.apache.commons.lang3.StringUtils.leftPad;

@Component
public class StructuredCommunicationFactory {

    private final DocumentRepository documentRepository;

    public StructuredCommunicationFactory(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public String create() {
        boolean uniqueNumber = false;
        String structuredCommunication = null;
        while (!uniqueNumber) {
            StringBuilder stringBuilder = new StringBuilder();
            Random rand = new Random();
            for (int i = 0; i < 10; i++) {
                stringBuilder.append(rand.nextInt(10));
            }
            long controlNumber = Long.parseLong(stringBuilder.toString()) % 97;
            if (controlNumber == 0) {
                stringBuilder.append("97");
            } else {
                stringBuilder.append(leftPad(valueOf(controlNumber), 2, "0"));
            }
            structuredCommunication = stringBuilder.toString();
            if (structuredCommunication.length() != 12) {
                throw new UnsupportedOperationException(structuredCommunication + " is invalid");
            }
            if (documentRepository.findDocumentByStructuredCommunication(structuredCommunication).isEmpty()) {
                uniqueNumber = true;
            }
        }

        return structuredCommunication;
    }
}
