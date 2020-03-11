package com.frans.bcmanager.controller;

import com.frans.bcmanager.factory.UrlFactory;
import com.frans.bcmanager.model.Document;
import com.frans.bcmanager.model.DocumentLine;
import com.frans.bcmanager.model.Mode;
import com.frans.bcmanager.model.ProjectInvoice;
import com.frans.bcmanager.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/clients/{client}/projects/{project}/documents/")
public class ProjectInvoiceController {

    private DocumentService documentService;

    private UrlFactory urlFactory;

    @Autowired
    public ProjectInvoiceController(DocumentService documentService,
                                    UrlFactory urlFactory) {
        this.documentService = documentService;
        this.urlFactory = urlFactory;
    }

    @GetMapping("/{id}")
    public String showProjectInvoiceDocument(@PathVariable("client") long clientId,
                                             @PathVariable("id") long documentId,
                                             @PathVariable("project") long projectId,
                                             Model model) {
        ProjectInvoice document = (ProjectInvoice) documentService.find(documentId);
        DocumentLine documentLine = new DocumentLine();
        model.addAttribute("clientId", clientId);
        model.addAttribute("document", document);
        model.addAttribute("documentLine", documentLine);
        model.addAttribute("projectId", projectId);
        model.addAttribute("mode", Mode.NEW);
        model.addAttribute("url", urlFactory.newProjectInvoiceDocumentLine(clientId, projectId, documentId));

        return "project_invoice_detail";
    }

    @GetMapping("/create")
    public String createProjectInvoiceDocument(@PathVariable("client") long clientId,
                                               @PathVariable("project") long projectId,
                                               Model model) {
        ProjectInvoice projectInvoice = new ProjectInvoice();
        model.addAttribute("clientId", clientId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("projectInvoice", projectInvoice);
        model.addAttribute("mode", Mode.NEW);
        model.addAttribute("nextInvoiceCode", documentService.getNextInvoiceCode());
        return "project_invoice_form";
    }

    @PostMapping("/create")
    public String saveProjectInvoiceDocument(@Valid @ModelAttribute ProjectInvoice document,
                                             BindingResult bindingResult,
                                             @PathVariable("client") long clientId,
                                             @PathVariable("project") long projectId,
                                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("clientId", clientId);
            model.addAttribute("projectId", projectId);
            model.addAttribute("mode", Mode.NEW);
            model.addAttribute("nextInvoiceCode", document.getCode());
            return "project_invoice_form";
        }
        Document savedDocument = documentService.save(document);
        return "redirect:" + savedDocument.getLink() + "?createSuccess";
    }

    @GetMapping("/{id}/edit")
    public String editProjectInvoice(@PathVariable("client") long clientId,
                                     @PathVariable("project") long projectId,
                                     @PathVariable("id") long documentId,
                                     Model model) {
        ProjectInvoice serviceInvoice = (ProjectInvoice) documentService.find(documentId);
        model.addAttribute("projectInvoice", serviceInvoice);
        model.addAttribute("clientId", clientId);
        model.addAttribute("projectId", projectId);

        model.addAttribute("mode", Mode.EDIT);
        return "project_invoice_form";
    }

    @PostMapping("/{id}/edit")
    public String editProjectInvoice(@PathVariable("client") long clientId,
                                     @PathVariable("project") long projectId,
                                     @PathVariable("id") long documentId,
                                     @Valid @ModelAttribute ProjectInvoice serviceInvoice,
                                     BindingResult bindingResult,
                                     Model model) {
        if (bindingResult.hasErrors()) {

            model.addAttribute("mode", Mode.EDIT);
            model.addAttribute("clientId", clientId);
            model.addAttribute("projectId", projectId);

            return "project_invoice_form";
        }
        documentService.save(serviceInvoice);
        return "redirect:/clients/" + clientId + "/projects/" + projectId + "/documents/" + documentId + "?editSuccess";
    }

    @GetMapping("/{id}/copy")
    public String copyEstimate(@PathVariable("id") long documentId) {
        ProjectInvoice invoice = (ProjectInvoice) documentService.copyInvoice(documentId);
        return "redirect:" + invoice.getLink() + "?copySuccess";
    }

    @PostMapping("/{id}/delete")
    public String deleteProjectInvoiceDocument(@PathVariable("client") long clientId,
                                               @PathVariable("project") long projectId,
                                               @PathVariable("id") long documentId) {
        documentService.delete(documentId);
        return "redirect:/clients/" + clientId + "/projects/" + projectId;
    }

    @PostMapping("/{id}/addLine")
    public String addProjectInvoiceDocumentLine(@Valid @ModelAttribute DocumentLine documentLine,
                                                BindingResult bindingResult,
                                                @PathVariable("client") long clientId,
                                                @PathVariable("project") long projectId,
                                                @PathVariable("id") long documentId,
                                                Model model) {
        Document document = documentService.find(documentId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("document", document);
            model.addAttribute("clientId", clientId);
            model.addAttribute("projectId", projectId);

            model.addAttribute("mode", Mode.NEW);
            model.addAttribute("url", urlFactory.newProjectInvoiceDocumentLine(clientId, projectId, documentId));
            return "project_invoice_detail";
        }

        documentService.addDocumentLine(document, documentLine);
        return "redirect:" + document.getLink();
    }

    @GetMapping("/{id}/editLine/{documentLineId}")
    public String editProjectInvoiceDocumentLine(@PathVariable("client") long clientId,
                                                 @PathVariable("project") long projectId,
                                                 @PathVariable("id") long documentId,
                                                 @PathVariable("documentLineId") long documentLineId,
                                                 Model model) {
        Document document = documentService.find(documentId);

        DocumentLine documentLine = document.getDocumentLines().stream().filter(dl -> dl.getId().equals(documentLineId)).findFirst().get();
        model.addAttribute("document", document);
        model.addAttribute("clientId", clientId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("documentLine", documentLine);
        model.addAttribute("mode", Mode.EDIT);
        model.addAttribute("url", urlFactory.editProjectInvoiceDocumentLine(clientId, projectId, documentId, documentLineId));

        return "project_invoice_detail";
    }

    @PostMapping("/{id}/editLine/{documentLineId}")
    public String editProjectInvoiceDocumentLine(@Valid @ModelAttribute DocumentLine documentLine,
                                                 BindingResult bindingResult,
                                                 @PathVariable("client") long clientId,
                                                 @PathVariable("project") long projectId,
                                                 @PathVariable("id") long documentId,
                                                 @PathVariable("documentLineId") long documentLineId,
                                                 Model model) {
        Document document = documentService.find(documentId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("document", document);
            model.addAttribute("clientId", clientId);
            model.addAttribute("projectId", projectId);
            model.addAttribute("mode", Mode.EDIT);
            model.addAttribute("url", urlFactory.editProjectInvoiceDocumentLine(clientId, projectId, documentId, documentLineId));
            return "project_invoice_detail";
        }
        documentService.editDocumentLine(document, documentLine);
        return "redirect:" + document.getLink();
    }

    @GetMapping("/{id}/deleteLine/{documentLineId}")
    public String deleteDocumentLine(@PathVariable("client") long clientId,
                                     @PathVariable("project") long projectId,
                                     @PathVariable("id") long documentId,
                                     @PathVariable("documentLineId") long documentLineId) {
        Document document = documentService.find(documentId);
        document.deleteLine(documentLineId);
        documentService.save(document);
        return "redirect:" + document.getLink();
    }

    @GetMapping("/{id}/updateStatus")
    public String updateStatus(@PathVariable("client") long clientId,
                               @PathVariable("project") long projectId,
                               @PathVariable("id") long documentId) {
        Document document = documentService.find(documentId);
        documentService.updateStatus(document);
        return "redirect:" + document.getLink();
    }
}
