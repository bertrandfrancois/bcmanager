package com.frans.bcmanager.controller;

import com.frans.bcmanager.factory.UrlFactory;
import com.frans.bcmanager.model.Client;
import com.frans.bcmanager.model.Document;
import com.frans.bcmanager.model.DocumentLine;
import com.frans.bcmanager.model.Mode;
import com.frans.bcmanager.model.Project;
import com.frans.bcmanager.model.ProjectInvoice;
import com.frans.bcmanager.service.ClientService;
import com.frans.bcmanager.service.DocumentLineService;
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

@Controller
@RequestMapping("/clients/{clientId}/projects/{projectId}/documents/")
public class ProjectInvoiceController {

    private ClientService clientService;

    private DocumentService documentService;

    private ProjectService projectService;

    private UrlFactory urlFactory;

    private DocumentLineService documentLineService;

    @Autowired
    public ProjectInvoiceController(ClientService clientService,
                                    DocumentService documentService,
                                    UrlFactory urlFactory,
                                    DocumentLineService documentLineService,
                                    ProjectService projectService) {
        this.clientService = clientService;
        this.documentService = documentService;
        this.urlFactory = urlFactory;
        this.documentLineService = documentLineService;
        this.projectService = projectService;
    }

    @GetMapping("/{documentId}")
    public String showProjectInvoiceDocument(@PathVariable("clientId") long clientId,
                                             @PathVariable("documentId") long documentId,
                                             @PathVariable("projectId") long projectId,
                                             Model model) {
        Client client = clientService.find(clientId);
        Project project = projectService.find(projectId);
        ProjectInvoice document = (ProjectInvoice) documentService.find(documentId);
        DocumentLine documentLine = new DocumentLine();
        model.addAttribute("client", client);
        model.addAttribute("document", document);
        model.addAttribute("documentLine", documentLine);
        model.addAttribute("project", project);
        model.addAttribute("mode", Mode.NEW);
        model.addAttribute("url", urlFactory.newProjectInvoiceDocumentLine(clientId, projectId, documentId));

        return "project_invoice_detail";
    }

    @GetMapping("/create")
    public String createProjectInvoiceDocument(@PathVariable("clientId") long clientId,
                                               @PathVariable("projectId") long projectId,
                                               Model model) {
        Client client = clientService.find(clientId);
        Project project = projectService.find(projectId);
        ProjectInvoice projectInvoice = new ProjectInvoice();
        model.addAttribute("client", client);
        model.addAttribute("project", project);
        model.addAttribute("projectInvoice", projectInvoice);
        model.addAttribute("mode", Mode.NEW);
        return "project_invoice_form";
    }

    @PostMapping("/create")
    public String saveProjectInvoiceDocument(@Valid @ModelAttribute ProjectInvoice document,
                                             BindingResult bindingResult,
                                             @PathVariable("clientId") long clientId,
                                             @PathVariable("projectId") long projectId,
                                             Model model) {
        Client client = clientService.find(clientId);
        Project project = projectService.find(projectId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("client", client);
            model.addAttribute("project", project);
            model.addAttribute("mode", Mode.NEW);
            return "project_invoice_form";
        }
        document.setClient(client);
        document.setProject(project);
        Document savedDocument = documentService.save(document);
        return "redirect:/clients/" + clientId + "/projects/" + projectId + "/documents/" + savedDocument.getId() + "?createSuccess";
    }

    @GetMapping("/{documentId}/edit")
    public String editProjectInvoice(@PathVariable("clientId") long clientId,
                                     @PathVariable("projectId") long projectId,
                                     @PathVariable("documentId") long documentId,
                                     Model model) {
        Client client = clientService.find(clientId);
        Project project = projectService.find(projectId);
        ProjectInvoice serviceInvoice = (ProjectInvoice) documentService.find(documentId);
        model.addAttribute("projectInvoice", serviceInvoice);
        model.addAttribute("client", client);
        model.addAttribute("project", project);

        model.addAttribute("mode", Mode.EDIT);
        return "project_invoice_form";
    }

    @PostMapping("/{documentId}/edit")
    public String editProjectInvoice(@PathVariable("clientId") long clientId,
                                     @PathVariable("projectId") long projectId,
                                     @PathVariable("documentId") long documentId,
                                     @Valid @ModelAttribute ProjectInvoice serviceInvoice,
                                     BindingResult bindingResult,
                                     Model model) {
        if (bindingResult.hasErrors()) {

            Project project = projectService.find(projectId);
            Client client = clientService.find(clientId);
            model.addAttribute("mode", Mode.EDIT);
            model.addAttribute("client", client);
            model.addAttribute("project", project);

            return "project_invoice_form";
        }
        documentService.save(serviceInvoice);
        return "redirect:/clients/" + clientId + "/projects/" + projectId + "/documents/" + documentId + "?editSuccess";
    }

    @PostMapping("/{documentId}/delete")
    public String deleteProjectInvoiceDocument(@PathVariable("clientId") long clientId,
                                               @PathVariable("projectId") long projectId,
                                               @PathVariable("documentId") long documentId) {
        documentService.delete(documentId);
        return "redirect:/clients/" + clientId + "/projects/" + projectId;
    }

    @PostMapping("/{documentId}/addLine")
    public String addProjectInvoiceDocumentLine(@Valid @ModelAttribute DocumentLine documentLine,
                                                BindingResult bindingResult,
                                                @PathVariable("clientId") long clientId,
                                                @PathVariable("projectId") long projectId,
                                                @PathVariable("documentId") long documentId,
                                                Model model) {
        Document document = documentService.find(documentId);
        Client client = clientService.find(clientId);
        Project project = projectService.find(projectId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("document", document);
            model.addAttribute("client", client);
            model.addAttribute("project", project);

            model.addAttribute("mode", Mode.NEW);
            model.addAttribute("url", urlFactory.newProjectInvoiceDocumentLine(clientId, projectId, documentId));
            return "project_invoice_detail";
        }

        document.addDocumentLine(documentLine);
        documentService.save(document);
        return "redirect:/clients/" + clientId + "/projects/" + projectId + "/documents/" + document.getId();
    }

    @GetMapping("/{documentId}/editLine/{documentLineId}")
    public String editProjectInvoiceDocumentLine(@PathVariable("clientId") long clientId,
                                                 @PathVariable("projectId") long projectId,
                                                 @PathVariable("documentId") long documentId,
                                                 @PathVariable("documentLineId") long documentLineId,
                                                 Model model) {
        Document document = documentService.find(documentId);
        Client client = clientService.find(clientId);
        Project project = projectService.find(projectId);

        DocumentLine documentLine = document.getDocumentLines().stream().filter(dl -> dl.getId().equals(documentLineId)).findFirst().get();
        model.addAttribute("document", document);
        model.addAttribute("client", client);
        model.addAttribute("project", project);
        model.addAttribute("documentLine", documentLine);
        model.addAttribute("mode", Mode.EDIT);
        model.addAttribute("url", urlFactory.editProjectInvoiceDocumentLine(clientId, projectId, documentId, documentLineId));

        return "project_invoice_detail";
    }

    @PostMapping("/{documentId}/editLine/{documentLineId}")
    public String editProjectInvoiceDocumentLine(@Valid @ModelAttribute DocumentLine documentLine,
                                                 BindingResult bindingResult,
                                                 @PathVariable("clientId") long clientId,
                                                 @PathVariable("projectId") long projectId,
                                                 @PathVariable("documentId") long documentId,
                                                 @PathVariable("documentLineId") long documentLineId,
                                                 Model model) {
        Document document = documentService.find(documentId);
        Client client = clientService.find(clientId);
        Project project = projectService.find(projectId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("document", document);
            model.addAttribute("client", client);
            model.addAttribute("project", project);
            model.addAttribute("mode", Mode.EDIT);
            model.addAttribute("url", urlFactory.editProjectInvoiceDocumentLine(clientId, projectId, documentId, documentLineId));
            return "project_invoice_detail";
        }
        documentLineService.save(documentLine);
        return "redirect:/clients/" + clientId + "/projects/" + projectId + "/documents/" + document.getId();
    }

    @GetMapping("/{documentId}/deleteLine/{documentLineId}")
    public String deleteDocumentLine(@PathVariable("clientId") long clientId,
                                     @PathVariable("projectId") long projectId,
                                     @PathVariable("documentId") long documentId,
                                     @PathVariable("documentLineId") long documentLineId) {
        documentLineService.delete(documentLineId);
        return "redirect:/clients/" + clientId + "/projects/" + projectId + "/documents/" + documentId;
    }
}
