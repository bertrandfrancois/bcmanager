package com.frans.bcmanager.controller;

import com.frans.bcmanager.factory.UrlFactory;
import com.frans.bcmanager.model.Document;
import com.frans.bcmanager.model.DocumentLine;
import com.frans.bcmanager.model.Mode;
import com.frans.bcmanager.model.ServiceInvoice;
import com.frans.bcmanager.service.DocumentService;
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
@RequestMapping("/clients/{clientId}/services")
public class ServiceInvoiceController {

    private DocumentService documentService;

    private UrlFactory urlFactory;

    public ServiceInvoiceController(DocumentService documentService,
                                    UrlFactory urlFactory) {
        this.documentService = documentService;
        this.urlFactory = urlFactory;
    }

    @GetMapping("/{documentId}")
    public String showServiceInvoiceDocument(@PathVariable("clientId") long clientId,
                                             @PathVariable("documentId") long documentId,
                                             Model model) {
        ServiceInvoice document = (ServiceInvoice) documentService.find(documentId);
        DocumentLine documentLine = new DocumentLine();
        model.addAttribute("client", clientId);
        model.addAttribute("document", document);
        model.addAttribute("documentLine", documentLine);
        model.addAttribute("mode", Mode.NEW);
        model.addAttribute("url", urlFactory.newServiceInvoiceDocumentLine(clientId, documentId));

        return "service_invoice_detail";
    }

    @GetMapping("/create")
    public String createServiceInvoiceDocument(@PathVariable("clientId") long clientId,
                                               Model model) {
        ServiceInvoice serviceInvoice = new ServiceInvoice();
        model.addAttribute("clientId", clientId);
        model.addAttribute("serviceInvoice", serviceInvoice);
        model.addAttribute("mode", Mode.NEW);
        model.addAttribute("nextInvoiceCode", documentService.getNextInvoiceCode());
        return "service_invoice_form";
    }

    @PostMapping("/create")
    public String saveServiceInvoiceDocument(@Valid @ModelAttribute ServiceInvoice document,
                                             BindingResult bindingResult,
                                             @PathVariable("clientId") long clientId,
                                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("clientId", clientId);
            model.addAttribute("mode", Mode.NEW);
            model.addAttribute("nextInvoiceCode", document.getCode());
            return "service_invoice_form";
        }
        Document savedDocument = documentService.save(document);
        return "redirect:/clients/" + clientId + "/services/" + savedDocument.getId() + "?createSuccess";
    }

    @GetMapping("/{documentId}/edit")
    public String editServiceInvoice(@PathVariable("clientId") long clientId,
                                     @PathVariable("documentId") long documentId,
                                     Model model) {
        ServiceInvoice serviceInvoice = (ServiceInvoice) documentService.find(documentId);
        model.addAttribute("serviceInvoice", serviceInvoice);
        model.addAttribute("clientId", clientId);
        model.addAttribute("mode", Mode.EDIT);
        return "service_invoice_form";
    }

    @PostMapping("/{documentId}/edit")
    public String editServiceInvoice(@PathVariable("clientId") long clientId,
                                     @PathVariable("documentId") long documentId,
                                     @Valid @ModelAttribute ServiceInvoice serviceInvoice,
                                     BindingResult bindingResult,
                                     Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", Mode.EDIT);
            model.addAttribute("clientId", clientId);
            return "service_invoice_form";
        }
        documentService.save(serviceInvoice);
        return "redirect:/clients/" + clientId + "/services/" + documentId + "?editSuccess";
    }

    @PostMapping("/{documentId}/delete")
    public String deleteServiceInvoiceDocument(@PathVariable("clientId") long clientId,
                                               @PathVariable("documentId") long documentId) {
        documentService.delete(documentId);
        return "redirect:/clients/" + clientId;
    }

    @PostMapping("/{documentId}/addLine")
    public String addServiceInvoiceDocumentLine(@Valid @ModelAttribute DocumentLine documentLine,
                                                BindingResult bindingResult,
                                                @PathVariable("clientId") long clientId,
                                                @PathVariable("documentId") long documentId,
                                                Model model) {
        Document document = documentService.find(documentId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("document", document);
            model.addAttribute("clientId", clientId);
            model.addAttribute("mode", Mode.NEW);
            model.addAttribute("url", urlFactory.newServiceInvoiceDocumentLine(clientId, documentId));
            return "service_invoice_detail";
        }

        document.addDocumentLine(documentLine);
        documentService.save(document);
        return "redirect:/clients/" + clientId + "/services/" + document.getId();
    }

    @GetMapping("/{documentId}/editLine/{documentLineId}")
    public String editServiceInvoiceDocumentLine(@PathVariable("clientId") long clientId,
                                                 @PathVariable("documentId") long documentId,
                                                 @PathVariable("documentLineId") long documentLineId,
                                                 Model model) {
        Document document = documentService.find(documentId);
        DocumentLine documentLine = document.getDocumentLines().stream().filter(dl -> dl.getId().equals(documentLineId)).findFirst().get();
        model.addAttribute("document", document);
        model.addAttribute("clientId", clientId);
        model.addAttribute("documentLine", documentLine);
        model.addAttribute("mode", Mode.EDIT);
        model.addAttribute("url", urlFactory.editServiceInvoiceDocumentLine(clientId, documentId, documentLineId));

        return "service_invoice_detail";
    }

    @PostMapping("/{documentId}/editLine/{documentLineId}")
    public String editServiceInvoiceDocumentLine(@Valid @ModelAttribute DocumentLine documentLine,
                                                 BindingResult bindingResult,
                                                 @PathVariable("clientId") long clientId,
                                                 @PathVariable("documentId") long documentId,
                                                 @PathVariable("documentLineId") long documentLineId,
                                                 Model model) {
        Document document = documentService.find(documentId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("document", document);
            model.addAttribute("clientId", clientId);
            model.addAttribute("mode", Mode.EDIT);
            model.addAttribute("url", urlFactory.editServiceInvoiceDocumentLine(clientId, documentId, documentLineId));
            return "service_invoice_detail";
        }
        document.editDocumentLine(documentLine);
        documentService.save(document);
        return "redirect:/clients/" + clientId + "/services/" + document.getId();
    }

    @GetMapping("/{documentId}/deleteLine/{documentLineId}")
    public String deleteDocumentLine(@PathVariable("clientId") long clientId,
                                     @PathVariable("documentId") long documentId,
                                     @PathVariable("documentLineId") long documentLineId) {
        Document document = documentService.find(documentId);
        document.deleteLine(documentLineId);
        documentService.save(document);
        return "redirect:/clients/" + clientId + "/services/" + documentId;
    }
}
