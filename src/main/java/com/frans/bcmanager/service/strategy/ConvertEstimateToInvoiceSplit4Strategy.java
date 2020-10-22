package com.frans.bcmanager.service.strategy;

import com.frans.bcmanager.enums.SplitMode;
import com.frans.bcmanager.enums.Unit;
import com.frans.bcmanager.model.ConversionDTO;
import com.frans.bcmanager.model.Document;
import com.frans.bcmanager.model.DocumentLine;
import com.frans.bcmanager.model.DocumentStatus;
import com.frans.bcmanager.model.Estimate;
import com.frans.bcmanager.model.ProjectInvoice;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;
import java.util.List;

import static java.math.BigDecimal.valueOf;

@Component
public class ConvertEstimateToInvoiceSplit4Strategy extends ConvertEstimateToInvoiceStrategy {

    @Override
    public Document split(Estimate estimate, ConversionDTO conversionDTO) {
        String lastInvoiceCode = documentRepository.getLastInvoiceCode().orElse(Year.now().toString() + "000");

        BigDecimal subTotal = estimate.getSubTotal();
        BigDecimal thirtyPercent = subTotal.multiply(valueOf(30.0 / 100.0)).setScale(2, RoundingMode.HALF_DOWN);
        BigDecimal tenPercent = subTotal.add(thirtyPercent.negate().multiply(valueOf(3.0))).setScale(2, RoundingMode.HALF_DOWN);

        ProjectInvoice invoice = createInvoice(estimate, conversionDTO, String.valueOf(Long.parseLong(lastInvoiceCode) + 1),
                                               documentLine(thirtyPercent, "accompte avant début des travaux"));
        documentRepository.save(invoice);
        ProjectInvoice invoice2 = createInvoice(estimate, conversionDTO, String.valueOf(Long.parseLong(lastInvoiceCode) + 2),
                                                documentLine(thirtyPercent, "facture d'avancement"));
        documentRepository.save(invoice2);
        ProjectInvoice invoice3 = createInvoice(estimate, conversionDTO, String.valueOf(Long.parseLong(lastInvoiceCode) + 3),
                                                documentLine(thirtyPercent, "facture d'avancement"));
        documentRepository.save(invoice3);
        ProjectInvoice invoice4 = createInvoice(estimate, conversionDTO, String.valueOf(Long.parseLong(lastInvoiceCode) + 4),
                                                documentLine(tenPercent, "facture de solde"));
        documentRepository.save(invoice4);

        estimate.setLinkedDocuments(List.of(invoice, invoice2, invoice3, invoice4));

        return invoice;
    }

    private DocumentLine documentLine(BigDecimal subTotal, String description) {
        return new DocumentLine(null, description, Unit.FF, BigDecimal.ONE, subTotal);
    }

    private ProjectInvoice createInvoice(Estimate estimate, ConversionDTO conversionDTO, String code, DocumentLine documentLine) {
        return ProjectInvoice.builder()
                             .code(code)
                             .creationDate(conversionDTO.getCreationDate())
                             .paymentDate(conversionDTO.getPaymentDate())
                             .taxRate(estimate.getTaxRate())
                             .project(conversionDTO.getProject())
                             .client(estimate.getClient())
                             .status(DocumentStatus.NOT_PAID)
                             .documentLines(List.of(documentLine))
                             .linkedDocuments(List.of(estimate))
                             .structuredCommunication(structuredCommunicationFactory.create())
                             .build();
    }

    @Override
    public boolean test(ConversionDTO conversionDTO) {
        return conversionDTO.getSplitMode() == SplitMode.SPLIT4;
    }
}
