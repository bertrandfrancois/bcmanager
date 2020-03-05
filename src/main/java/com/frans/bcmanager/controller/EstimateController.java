package com.frans.bcmanager.controller;

import com.frans.bcmanager.factory.UrlFactory;
import com.frans.bcmanager.model.Document;
import com.frans.bcmanager.model.DocumentLine;
import com.frans.bcmanager.model.Estimate;
import com.frans.bcmanager.model.Mode;
import com.frans.bcmanager.service.DocumentLineService;
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
@RequestMapping("/clients/{clientId}/estimates")
public class EstimateController {

    private DocumentService documentService;

    private UrlFactory urlFactory;

    @Autowired
    public EstimateController(DocumentService documentService,
                              UrlFactory urlFactory) {
        this.documentService = documentService;
        this.urlFactory = urlFactory;
    }

    @GetMapping("/{documentId}")
    public String showEstimateDocument(@PathVariable("clientId") long clientId,
                                       @PathVariable("documentId") long documentId,
                                       Model model) {
        Estimate document = (Estimate) documentService.find(documentId);
        DocumentLine documentLine = new DocumentLine();
        model.addAttribute("clientId", clientId);
        model.addAttribute("document", document);
        model.addAttribute("documentLine", documentLine);
        model.addAttribute("mode", Mode.NEW);
        model.addAttribute("url", urlFactory.newEstimateDocumentLine(clientId, documentId));

        return "estimate_detail";
    }

    @GetMapping("/create")
    public String createEstimateDocument(@PathVariable("clientId") long clientId,
                                         Model model) {
        Estimate estimate = new Estimate();
        model.addAttribute("clientId", clientId);
        model.addAttribute("estimate", estimate);
        model.addAttribute("mode", Mode.NEW);
        return "estimate_form";
    }

    @PostMapping("/create")
    public String saveEstimateDocument(@Valid @ModelAttribute Estimate document,
                                       BindingResult bindingResult,
                                       @PathVariable("clientId") long clientId,
                                       Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("clientId", clientId);
            model.addAttribute("mode", Mode.NEW);
            return "estimate_form";
        }
        Document savedDocument = documentService.save(document);
        return "redirect:/clients/" + clientId + "/estimates/" + savedDocument.getId() + "?createSuccess";
    }

    @GetMapping("/{documentId}/edit")
    public String editEstimate(@PathVariable("clientId") long clientId,
                               @PathVariable("documentId") long documentId,
                               Model model) {
        Estimate estimate = (Estimate) documentService.find(documentId);
        model.addAttribute("estimate", estimate);
        model.addAttribute("clientId", clientId);
        model.addAttribute("mode", Mode.EDIT);
        return "estimate_form";
    }

    @PostMapping("/{documentId}/edit")
    public String editEstimate(@PathVariable("clientId") long clientId,
                               @PathVariable("documentId") long documentId,
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

    @PostMapping("/{documentId}/delete")
    public String deleteEstimateDocument(@PathVariable("clientId") long clientId,
                                         @PathVariable("documentId") long documentId) {
        documentService.delete(documentId);
        return "redirect:/clients/" + clientId;
    }

    @PostMapping("/{documentId}/addLine")
    public String addEstimateDocumentLine(@Valid @ModelAttribute DocumentLine documentLine,
                                          BindingResult bindingResult,
                                          @PathVariable("clientId") long clientId,
                                          @PathVariable("documentId") long documentId,
                                          Model model) {
        Document document = documentService.find(documentId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("document", document);
            model.addAttribute("clientId", clientId);
            model.addAttribute("mode", Mode.NEW);
            model.addAttribute("url", urlFactory.newEstimateDocumentLine(clientId, documentId));
            return "estimate_detail";
        }

        document.addDocumentLine(documentLine);
        documentService.save(document);
        return "redirect:/clients/" + clientId + "/estimates/" + document.getId();
    }

    @GetMapping("/{documentId}/editLine/{documentLineId}")
    public String editEstimateDocumentLine(@PathVariable("clientId") long clientId,
                                           @PathVariable("documentId") long documentId,
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

    @PostMapping("/{documentId}/editLine/{documentLineId}")
    public String editEstimateDocumentLine(@Valid @ModelAttribute DocumentLine documentLine,
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
            model.addAttribute("url", urlFactory.editEstimateDocumentLine(clientId, documentId, documentLineId));
            return "estimate_detail";
        }
        document.editDocumentLine(documentLine);
        documentService.save(document);
        return "redirect:/clients/" + clientId + "/estimates/" + document.getId();
    }

    @GetMapping("/{documentId}/deleteLine/{documentLineId}")
    public String deleteDocumentLine(@PathVariable("clientId") long clientId,
                                     @PathVariable("documentId") long documentId,
                                     @PathVariable("documentLineId") long documentLineId) {
        Document document = documentService.find(documentId);
        document.deleteLine(documentLineId);
        documentService.save(document);
        return "redirect:/clients/" + clientId + "/estimates/" + documentId;
    }
}