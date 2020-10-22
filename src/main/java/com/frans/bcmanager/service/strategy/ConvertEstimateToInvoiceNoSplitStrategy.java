package com.frans.bcmanager.service.strategy;

import com.frans.bcmanager.enums.SplitMode;
import com.frans.bcmanager.model.ConversionDTO;
import com.frans.bcmanager.model.Document;
import com.frans.bcmanager.model.DocumentLine;
import com.frans.bcmanager.model.DocumentStatus;
import com.frans.bcmanager.model.Estimate;
import com.frans.bcmanager.model.ProjectInvoice;
import com.frans.bcmanager.model.ServiceInvoice;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConvertEstimateToInvoiceNoSplitStrategy extends ConvertEstimateToInvoiceStrategy {

    @Override
    public Document split(Estimate estimate, ConversionDTO conversionDTO) {
        List<DocumentLine> documentLines = Lists.newArrayList();
        estimate.getDocumentLines().forEach(cloneDocumentLines(documentLines));
        if (conversionDTO.getProject() == null) {

            ServiceInvoice serviceInvoice = ServiceInvoice.builder()
                                                          .code(conversionDTO.getCode())
                                                          .creationDate(conversionDTO.getCreationDate())
                                                          .paymentDate(conversionDTO.getPaymentDate())
                                                          .taxRate(estimate.getTaxRate())
                                                          .client(estimate.getClient())
                                                          .status(DocumentStatus.NOT_PAID)
                                                          .documentLines(documentLines)
                                                          .linkedDocuments(List.of(estimate))
                                                          .structuredCommunication(structuredCommunicationFactory.create())
                                                          .build();
            documentRepository.save(serviceInvoice);
            estimate.setLinkedDocuments(List.of(serviceInvoice));
            return serviceInvoice;
        }
        ProjectInvoice projectInvoice = ProjectInvoice.builder()
                                                      .code(conversionDTO.getCode())
                                                      .creationDate(conversionDTO.getCreationDate())
                                                      .paymentDate(conversionDTO.getPaymentDate())
                                                      .taxRate(estimate.getTaxRate())
                                                      .project(conversionDTO.getProject())
                                                      .client(estimate.getClient())
                                                      .status(DocumentStatus.NOT_PAID)
                                                      .documentLines(documentLines)
                                                      .linkedDocuments(List.of(estimate))
                                                      .structuredCommunication(structuredCommunicationFactory.create())
                                                      .build();
        documentRepository.save(projectInvoice);
        estimate.setLinkedDocuments(List.of(projectInvoice));
        return projectInvoice;
    }

    @Override
    public boolean test(ConversionDTO conversionDTO) {
        return conversionDTO.getSplitMode() == SplitMode.NONE;
    }
}
