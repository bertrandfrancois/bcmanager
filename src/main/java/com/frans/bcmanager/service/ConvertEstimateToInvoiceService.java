package com.frans.bcmanager.service;

import com.frans.bcmanager.model.ConversionDTO;
import com.frans.bcmanager.model.Document;
import com.frans.bcmanager.model.DocumentLine;
import com.frans.bcmanager.model.DocumentStatus;
import com.frans.bcmanager.model.Estimate;
import com.frans.bcmanager.model.ProjectInvoice;
import com.frans.bcmanager.model.ServiceInvoice;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
public class ConvertEstimateToInvoiceService {

    public Document convert(Estimate estimate, ConversionDTO conversionDTO) {
        List<DocumentLine> documentLines = Lists.newArrayList();
        estimate.getDocumentLines().forEach(cloneDocumentLines(documentLines));
        if (conversionDTO.getProject() == null) {

            return ServiceInvoice.builder()
                                 .code(conversionDTO.getCode())
                                 .creationDate(conversionDTO.getCreationDate())
                                 .paymentDate(conversionDTO.getPaymentDate())
                                 .taxRate(estimate.getTaxRate())
                                 .client(estimate.getClient())
                                 .status(DocumentStatus.NOT_PAID)
                                 .documentLines(documentLines)
                                 .linkedDocument(estimate)
                                 .structuredCommunication(StructuredCommunicationFactory.create())
                                 .build();
        }
        return ProjectInvoice.builder()
                             .code(conversionDTO.getCode())
                             .creationDate(conversionDTO.getCreationDate())
                             .paymentDate(conversionDTO.getPaymentDate())
                             .taxRate(estimate.getTaxRate())
                             .project(conversionDTO.getProject())
                             .client(estimate.getClient())
                             .status(DocumentStatus.NOT_PAID)
                             .documentLines(documentLines)
                             .linkedDocument(estimate)
                             .structuredCommunication(StructuredCommunicationFactory.create())
                             .build();
    }

    private Consumer<DocumentLine> cloneDocumentLines(List<DocumentLine> documentLines) {
        return dl -> {
            try {
                documentLines.add((DocumentLine) dl.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        };
    }
}
