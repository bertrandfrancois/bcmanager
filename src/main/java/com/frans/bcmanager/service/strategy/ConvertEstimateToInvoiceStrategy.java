package com.frans.bcmanager.service.strategy;

import com.frans.bcmanager.model.ConversionDTO;
import com.frans.bcmanager.model.Document;
import com.frans.bcmanager.model.DocumentLine;
import com.frans.bcmanager.model.Estimate;
import com.frans.bcmanager.repository.DocumentRepository;
import com.frans.bcmanager.service.StructuredCommunicationFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class ConvertEstimateToInvoiceStrategy implements Predicate<ConversionDTO> {

    @Autowired
    protected StructuredCommunicationFactory structuredCommunicationFactory;

    @Autowired
    protected DocumentRepository documentRepository;

    public abstract Document split(Estimate estimate, ConversionDTO conversionDTO);

    protected Consumer<DocumentLine> cloneDocumentLines(List<DocumentLine> documentLines) {
        return dl -> {
            try {
                documentLines.add((DocumentLine) dl.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        };
    }
}
