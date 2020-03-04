package com.frans.bcmanager.controller;

import com.frans.bcmanager.model.ProjectInvoice;
import com.frans.bcmanager.pdf.ProjectInvoicePdfView;
import com.frans.bcmanager.service.DocumentService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class ProjectInvoiceViewController {

    private final DocumentService documentService;

    public ProjectInvoiceViewController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/display/projectInvoice/{documentId}")
    public ModelAndView document(@PathVariable("documentId") long id, ModelAndView model) {
        ProjectInvoice document = (ProjectInvoice) documentService.find(id);
        HashMap<String, Object> parameters = Maps.newHashMap();
        parameters.put("document", document);

        return new ModelAndView(new ProjectInvoicePdfView(), parameters);
    }
}
