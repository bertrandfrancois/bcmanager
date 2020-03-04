package com.frans.bcmanager.controller;

import com.frans.bcmanager.model.Client;
import com.frans.bcmanager.model.Mode;
import com.frans.bcmanager.model.Project;
import com.frans.bcmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients")
    public String clients(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("clients", clientService.findAll());
        return "clients";
    }

    @GetMapping("/clients/create")
    public String createClient(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("mode", Mode.NEW);
        return "client_form";
    }

    @PostMapping("/clients/create")
    public String createClient(@Valid @ModelAttribute Client client, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", Mode.NEW);
            return "client_form";
        }
        Client newClient = clientService.save(client);

        return "redirect:/clients/" + newClient.getId() + "?createSuccess";
    }

    @GetMapping("/clients/{id}")
    public String showClient(@PathVariable long id, Model model) {
        Client client = clientService.find(id);
        Project project = new Project();
        model.addAttribute("project", project);
        model.addAttribute("client", client);
        model.addAttribute("clientId", client.getId());
        return "client_detail";
    }

    @GetMapping("/clients/{id}/edit")
    public String editClient(@PathVariable long id, Model model) {
        Client client = clientService.find(id);
        model.addAttribute("client", client);
        model.addAttribute("mode", Mode.EDIT);
        return "client_form";
    }

    @PostMapping("/clients/{id}/edit")
    public String editClient(@PathVariable long id, @Valid @ModelAttribute Client client, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", Mode.EDIT);
            return "client_form";
        }
        clientService.save(client);
        return "redirect:/clients/" + id + "?editSuccess";
    }

    @PostMapping("/clients/{id}/delete")
    public String deleteClient(@PathVariable long id) {
        clientService.delete(id);
        return "redirect:/clients";
    }
}
