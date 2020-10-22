package com.frans.bcmanager.service;

import com.frans.bcmanager.model.ConversionDTO;
import com.frans.bcmanager.model.Document;
import com.frans.bcmanager.model.Estimate;
import com.frans.bcmanager.service.strategy.ConvertEstimateToInvoiceStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConvertEstimateToInvoiceService {

    private final List<ConvertEstimateToInvoiceStrategy> convertEstimateToInvoiceStrategies;

    public ConvertEstimateToInvoiceService(List<ConvertEstimateToInvoiceStrategy> convertEstimateToInvoiceStrategies) {
        this.convertEstimateToInvoiceStrategies = convertEstimateToInvoiceStrategies;
    }

    public Document convert(Estimate estimate, ConversionDTO conversionDTO) {
        return convertEstimateToInvoiceStrategies
                .stream().filter(strategy -> strategy.test(conversionDTO))
                .findFirst()
                .map(strategy -> strategy.split(estimate, conversionDTO))
                .orElse(null);
    }
}
