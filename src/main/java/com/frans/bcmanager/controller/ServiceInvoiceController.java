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
@RequestMapping("/clients/{client}/services")
public class ServiceInvoiceController {

    private DocumentService documentService;

    private UrlFactory urlFactory;

    public ServiceInvoiceController(DocumentService documentService,
                                    UrlFactory urlFactory) {
        this.documentService = documentService;
        this.urlFactory = urlFactory;
    }

    @GetMapping("/{id}")
    public String showServiceInvoiceDocument(@PathVariable("client") long clientId,
                                             @PathVariable("id") long id,
                                             Model model) {
        ServiceInvoice document = (ServiceInvoice) documentService.find(id);
        DocumentLine documentLine = new DocumentLine();
        model.addAttribute("document", document);
        model.addAttribute("documentLine", documentLine);
        model.addAttribute("mode", Mode.NEW);
        model.addAttribute("url", urlFactory.newServiceInvoiceDocumentLine(clientId, id));

        return "service_invoice_detail";
    }

    @GetMapping("/create")
    public String createServiceInvoiceDocument(@PathVariable("client") long clientId,
                                               Model model) {
        ServiceInvoice serviceInvoice = ServiceInvoice.create();
        model.addAttribute("clientId", clientId);
        model.addAttribute("serviceInvoice", serviceInvoice);
        model.addAttribute("mode", Mode.NEW);
        model.addAttribute("nextInvoiceCode", documentService.getNextInvoiceCode());
        return "service_invoice_form";
    }

    @PostMapping("/create")
    public String saveServiceInvoiceDocument(@Valid @ModelAttribute ServiceInvoice document,
                                             BindingResult bindingResult,
                                             @PathVariable("client") long clientId,
                                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("clientId", clientId);
            model.addAttribute("mode", Mode.NEW);
            model.addAttribute("nextInvoiceCode", document.getCode());
            return "service_invoice_form";
        }
        Document savedDocument = documentService.save(document);
        return "redirect:" + savedDocument.getLink() + "?createSuccess";
    }

    @GetMapping("/{id}/edit")
    public String editServiceInvoice(@PathVariable("client") long clientId,
                                     @PathVariable("id") long id,
                                     Model model) {
        ServiceInvoice serviceInvoice = (ServiceInvoice) documentService.find(id);
        model.addAttribute("serviceInvoice", serviceInvoice);
        model.addAttribute("clientId", clientId);
        model.addAttribute("mode", Mode.EDIT);
        return "service_invoice_form";
    }

    @PostMapping("/{id}/edit")
    public String editServiceInvoice(@PathVariable("client") long clientId,
                                     @PathVariable("id") long id,
                                     @Valid @ModelAttribute ServiceInvoice serviceInvoice,
                                     BindingResult bindingResult,
                                     Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", Mode.EDIT);
            model.addAttribute("clientId", clientId);
            return "service_invoice_form";
        }
        Document document = documentService.save(serviceInvoice);

        return "redirect:" + document.getLink() + "?editSuccess";
    }

    @GetMapping("/{id}/copy")
    public String copyEstimate(@PathVariable("id") long id) {
        ServiceInvoice invoice = (ServiceInvoice) documentService.copyInvoice(id);
        return "redirect:" + invoice.getLink() + "?copySuccess";
    }

    @PostMapping("/{id}/delete")
    public String deleteServiceInvoiceDocument(@PathVariable("client") long clientId,
                                               @PathVariable("id") long id) {
        documentService.delete(id);
        return "redirect:/clients/" + clientId;
    }

    @PostMapping("/{id}/addLine")
    public String addServiceInvoiceDocumentLine(@Valid @ModelAttribute DocumentLine documentLine,
                                                BindingResult bindingResult,
                                                @PathVariable("client") long clientId,
                                                @PathVariable("id") long id,
                                                Model model) {
        Document document = documentService.find(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("document", document);
            model.addAttribute("mode", Mode.NEW);
            model.addAttribute("url", urlFactory.newServiceInvoiceDocumentLine(clientId, id));
            return "service_invoice_detail";
        }
        documentService.addDocumentLine(document, documentLine);
        return "redirect:" + document.getLink();
    }

    @GetMapping("/{id}/editLine/{documentLineId}")
    public String editServiceInvoiceDocumentLine(@PathVariable("client") long clientId,
                                                 @PathVariable("id") long id,
                                                 @PathVariable("documentLineId") long documentLineId,
                                                 Model model) {
        Document document = documentService.find(id);
        DocumentLine documentLine = document.getDocumentLines().stream().filter(dl -> dl.getId().equals(documentLineId)).findFirst().get();
        model.addAttribute("document", document);
        model.addAttribute("documentLine", documentLine);
        model.addAttribute("mode", Mode.EDIT);
        model.addAttribute("url", urlFactory.editServiceInvoiceDocumentLine(clientId, id, documentLineId));

        return "service_invoice_detail";
    }

    @PostMapping("/{id}/editLine/{documentLineId}")
    public String editServiceInvoiceDocumentLine(@Valid @ModelAttribute DocumentLine documentLine,
                                                 BindingResult bindingResult,
                                                 @PathVariable("client") long clientId,
                                                 @PathVariable("id") long id,
                                                 @PathVariable("documentLineId") long documentLineId,
                                                 Model model) {
        Document document = documentService.find(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("document", document);
            model.addAttribute("mode", Mode.EDIT);
            model.addAttribute("url", urlFactory.editServiceInvoiceDocumentLine(clientId, id, documentLineId));
            return "service_invoice_detail";
        }
        documentService.editDocumentLine(document, documentLine);
        return "redirect:" + document.getLink();
    }

    @GetMapping("/{id}/deleteLine/{documentLineId}")
    public String deleteDocumentLine(@PathVariable("client") long clientId,
                                     @PathVariable("id") long id,
                                     @PathVariable("documentLineId") long documentLineId) {
        Document document = documentService.find(id);
        documentService.deleteDocumentLine(document, documentLineId);
        return "redirect:" + document.getLink();
    }

    @GetMapping("/{id}/updateStatus")
    public String updateStatus(@PathVariable("client") long clientId,
                               @PathVariable("id") long id) {
        Document document = documentService.find(id);
        documentService.updateStatus(document);
        return "redirect:" + document.getLink();
    }
}
