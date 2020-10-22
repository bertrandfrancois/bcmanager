package com.frans.bcmanager.controller;

import com.frans.bcmanager.model.Client;
import com.frans.bcmanager.model.Document;
import com.frans.bcmanager.model.Project;
import com.frans.bcmanager.service.ClientService;
import com.frans.bcmanager.service.DocumentService;
import com.frans.bcmanager.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomeController {

    private final ClientService clientService;
    private final DocumentService documentService;
    private final ProjectService projectService;

    public HomeController(ClientService clientService,
                          DocumentService documentService,
                          ProjectService projectService) {
        this.clientService = clientService;
        this.documentService = documentService;
        this.projectService = projectService;
    }

    @GetMapping({"/home", "/"})
    public String home(Model model) {
        setupHomePageData(model);
        return "home";
    }

    @PostMapping("/home")
    public String loginHome(Model model) {
        setupHomePageData(model);
        return "home";
    }

    private void setupHomePageData(Model model) {
        List<Client> clients = clientService.findTop5();
        List<Document> unpaidDocuments = documentService.getUnpaidDocuments();
        List<Project> projects = projectService.findTop5();
        model.addAttribute("clients", clients);
        model.addAttribute("projects", projects);
        model.addAttribute("unpaidDocuments", unpaidDocuments);
    }

    @GetMapping("/clients")
    public String showClients(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("clients", clientService.findAll());
        return "clients";
    }

    @GetMapping("/projects")
    public String showProjects(Model model) {
        List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);

        return "projects";
    }

    @GetMapping("/invoices")
    public String showInvoices(Model model) {
        List<Document> documents = documentService.findAllInvoices();
        model.addAttribute("documents", documents);

        return "invoices";
    }

    @GetMapping("/estimates")
    public String showEstimates(Model model) {
        List<Document> documents = documentService.findAllEstimates();
        model.addAttribute("documents", documents);

        return "estimates";
    }

    @GetMapping("/documents")
    public String showDocuments(Model model) {
        List<Document> documents = documentService.findAll();
        model.addAttribute("documents", documents);

        return "invoices";
    }
}
