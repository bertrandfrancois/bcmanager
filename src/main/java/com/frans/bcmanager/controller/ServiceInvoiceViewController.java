package com.frans.bcmanager.controller;

import com.frans.bcmanager.model.ServiceInvoice;
import com.frans.bcmanager.pdf.PdfDocumentView;
import com.frans.bcmanager.service.DocumentService;
import com.frans.bcmanager.service.EnterpriseService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class ServiceInvoiceViewController {

    private final DocumentService documentService;
    private final EnterpriseService enterpriseService;

    public ServiceInvoiceViewController(DocumentService documentService, EnterpriseService enterpriseService) {
        this.documentService = documentService;
        this.enterpriseService = enterpriseService;
    }

    @GetMapping("/display/serviceInvoice/{documentId}")
    public ModelAndView document(@PathVariable("documentId") long id, ModelAndView model) {
        ServiceInvoice document = (ServiceInvoice) documentService.find(id);
        HashMap<String, Object> parameters = Maps.newHashMap();
        parameters.put("document", document);
        parameters.put("enterprise", enterpriseService.getEnterprise().get());
        parameters.put("documentType", "FACTURE");

        return new ModelAndView(new PdfDocumentView(), parameters);
    }
}
