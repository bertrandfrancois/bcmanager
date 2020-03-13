package com.frans.bcmanager.controller;

import com.frans.bcmanager.factory.UrlFactory;
import com.frans.bcmanager.model.ConversionDTO;
import com.frans.bcmanager.model.Document;
import com.frans.bcmanager.model.DocumentLine;
import com.frans.bcmanager.model.DocumentStatus;
import com.frans.bcmanager.model.Estimate;
import com.frans.bcmanager.model.Mode;
import com.frans.bcmanager.model.Project;
import com.frans.bcmanager.service.DocumentService;
import com.frans.bcmanager.service.ProjectService;
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
import java.util.List;

@Controller
@RequestMapping("/clients/{client}/estimates")
public class EstimateController {

    private final DocumentService documentService;
    private final ProjectService projectService;
    private final UrlFactory urlFactory;

    @Autowired
    public EstimateController(DocumentService documentService,
                              ProjectService projectService,
                              UrlFactory urlFactory) {
        this.documentService = documentService;
        this.projectService = projectService;
        this.urlFactory = urlFactory;
    }

    @GetMapping("/{id}")
    public String showEstimateDocument(@PathVariable("client") long clientId,
                                       @PathVariable("id") long documentId,
                                       Model model) {
        Estimate document = (Estimate) documentService.find(documentId);
        DocumentLine documentLine = new DocumentLine();
        model.addAttribute("clientId", clientId);
        model.addAttribute("document", document);
        model.addAttribute("documentLine", documentLine);
        if (document.getStatus() == DocumentStatus.ACCEPTED) {
            List<Project> projects = projectService.findAll();
            model.addAttribute("projects", projects);
            model.addAttribute("conversionDTO", new ConversionDTO(documentService.getNextInvoiceCode()));
            model.addAttribute("url", urlFactory.convertToInvoice(clientId, documentId));
        } else {
            model.addAttribute("mode", Mode.NEW);
            model.addAttribute("url", urlFactory.newEstimateDocumentLine(clientId, documentId));
        }
        return "estimate_detail";
    }

    @PostMapping("/{id}/convertToInvoice")
    public String convertToInvoice(@Valid @ModelAttribute ConversionDTO conversionDTO,
                                   BindingResult bindingResult,
                                   @PathVariable("client") long clientId,
                                   @PathVariable("id") long documentId,
                                   Model model) {
        Estimate document = (Estimate) documentService.find(documentId);
        if (bindingResult.hasErrors()) {
            List<Project> projects = projectService.findAll();
            model.addAttribute("clientId", clientId);
            model.addAttribute("document", document);
            model.addAttribute("projects", projects);
            model.addAttribute("url", urlFactory.convertToInvoice(clientId, documentId));
            return "estimate_detail";
        }
        Document invoice = documentService.convertEstimateToInvoice(document, conversionDTO);
        return "redirect:" + invoice.getLink() + "?createSuccess";
    }

    @GetMapping("/create")
    public String createEstimateDocument(@PathVariable("client") long clientId,
                                         Model model) {
        Estimate estimate = new Estimate();
        model.addAttribute("clientId", clientId);
        model.addAttribute("estimate", estimate);
        model.addAttribute("mode", Mode.NEW);
        model.addAttribute("nextEstimateCode", documentService.getNextEstimateCode());
        return "estimate_form";
    }

    @PostMapping("/create")
    public String saveEstimateDocument(@Valid @ModelAttribute Estimate document,
                                       BindingResult bindingResult,
                                       @PathVariable("client") long clientId,
                                       Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("clientId", clientId);
            model.addAttribute("mode", Mode.NEW);
            model.addAttribute("nextEstimateCode", document.getCode());
            return "estimate_form";
        }
        Document savedDocument = documentService.save(document);
        return "redirect:" + savedDocument.getLink() + "?createSuccess";
    }

    @GetMapping("/{id}/edit")
    public String editEstimate(@PathVariable("client") long clientId,
                               @PathVariable("id") long documentId,
                               Model model) {
        Estimate estimate = (Estimate) documentService.find(documentId);
        model.addAttribute("estimate", estimate);
        model.addAttribute("clientId", clientId);
        model.addAttribute("mode", Mode.EDIT);
        return "estimate_form";
    }

    @GetMapping("/{id}/copy")
    public String copyEstimate(@PathVariable("id") long documentId) {
        Estimate estimate = documentService.copyEstimate(documentId);
        return "redirect:" + estimate.getLink() + "?copySuccess";
    }

    @PostMapping("/{id}/edit")
    public String editEstimate(@PathVariable("client") long clientId,
                               @PathVariable("id") long documentId,
                               @Valid @ModelAttribute Estimate estimate,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", Mode.EDIT);
            model.addAttribute("clientId", clientId);
            return "estimate_form";
        }
        documentService.save(estimate);
        return "redirect:/clients/" + clientId + "/estimates/" + documentId + "?editSuccess";
    }

    @PostMapping("/{id}/delete")
    public String deleteEstimateDocument(@PathVariable("client") long clientId,
                                         @PathVariable("id") long documentId) {
        documentService.delete(documentId);
        return "redirect:/clients/" + clientId;
    }

    @PostMapping("/{id}/addLine")
    public String addEstimateDocumentLine(@Valid @ModelAttribute DocumentLine documentLine,
                                          BindingResult bindingResult,
                                          @PathVariable("client") long clientId,
                                          @PathVariable("id") long documentId,
                                          Model model) {
        Document document = documentService.find(documentId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("document", document);
            model.addAttribute("clientId", clientId);
            model.addAttribute("mode", Mode.NEW);
            model.addAttribute("url", urlFactory.newEstimateDocumentLine(clientId, documentId));
            return "estimate_detail";
        }
        documentService.addDocumentLine(document, documentLine);
        return "redirect:" + document.getLink();
    }

    @GetMapping("/{id}/updateStatus")
    public String updateStatus(@PathVariable("client") long clientId,
                               @PathVariable("id") long id) {
        Document document = documentService.find(id);
        documentService.updateStatus(document);
        return "redirect:" + document.getLink();
    }

    @GetMapping("/{id}/editLine/{documentLineId}")
    public String editEstimateDocumentLine(@PathVariable("client") long clientId,
                                           @PathVariable("id") long documentId,
                                           @PathVariable("documentLineId") long documentLineId,
                                           Model model) {
        Document document = documentService.find(documentId);
        DocumentLine documentLine = document.getDocumentLines().stream().filter(dl -> dl.getId().equals(documentLineId)).findFirst().get();
        model.addAttribute("document", document);
        model.addAttribute("clientId", clientId);
        model.addAttribute("documentLine", documentLine);
        model.addAttribute("mode", Mode.EDIT);
        model.addAttribute("url", urlFactory.editEstimateDocumentLine(clientId, documentId, documentLineId));

        return "estimate_detail";
    }

    @PostMapping("/{id}/editLine/{documentLineId}")
    public String editEstimateDocumentLine(@Valid @ModelAttribute DocumentLine documentLine,
                                           BindingResult bindingResult,
                                           @PathVariable("client") long clientId,
                                           @PathVariable("id") long documentId,
                                           @PathVariable("documentLineId") long documentLineId,
                                           Model model) {
        Document document = documentService.find(documentId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("document", document);
            model.addAttribute("clientId", clientId);
            model.addAttribute("mode", Mode.EDIT);
            model.addAttribute("url", urlFactory.editEstimateDocumentLine(clientId, documentId, documentLineId));
            return "estimate_detail";
        }
        documentService.editDocumentLine(document, documentLine);
        return "redirect:" + document.getLink();
    }

    @GetMapping("/{id}/deleteLine/{documentLineId}")
    public String deleteDocumentLine(@PathVariable("client") long clientId,
                                     @PathVariable("id") long documentId,
                                     @PathVariable("documentLineId") long documentLineId) {
        Document document = documentService.find(documentId);
        documentService.deleteDocumentLine(document, documentLineId);
        return "redirect:" + document.getLink();
    }
}
