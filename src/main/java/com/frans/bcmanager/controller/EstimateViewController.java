package com.frans.bcmanager.controller;

import com.frans.bcmanager.model.Estimate;
import com.frans.bcmanager.pdf.PdfDocumentView;
import com.frans.bcmanager.service.DocumentService;
import com.frans.bcmanager.service.EnterpriseService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class EstimateViewController {

    private final DocumentService documentService;
    private final EnterpriseService enterpriseService;

    @Autowired
    public EstimateViewController(DocumentService documentService, EnterpriseService enterpriseService) {
        this.documentService = documentService;
        this.enterpriseService = enterpriseService;
    }

    @GetMapping("/display/estimate/{documentId}")
    public ModelAndView document(@PathVariable("documentId") long id, ModelAndView model) {
        Estimate document = (Estimate) documentService.find(id);
        HashMap<String, Object> parameters = Maps.newHashMap();
        parameters.put("document", document);
        parameters.put("enterprise", enterpriseService.getEnterprise().get());
        parameters.put("documentType", "DEVIS");

        return new ModelAndView(new PdfDocumentView(), parameters);
    }
}
