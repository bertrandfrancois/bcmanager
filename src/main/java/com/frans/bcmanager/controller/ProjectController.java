package com.frans.bcmanager.controller;

import com.frans.bcmanager.factory.UrlFactory;
import com.frans.bcmanager.model.Mode;
import com.frans.bcmanager.model.Project;
import com.frans.bcmanager.service.ProjectService;
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
@RequestMapping("/clients/{clientId}/projects")
public class ProjectController {

    private ProjectService projectService;

    private UrlFactory urlFactory;

    public ProjectController(ProjectService projectService,
                             UrlFactory urlFactory) {
        this.projectService = projectService;
        this.urlFactory = urlFactory;
    }

    @GetMapping("/{projectId}")
    public String showProject(@PathVariable("clientId") long clientId, @PathVariable("projectId") long projectId, Model model) {
        Project project = projectService.find(projectId);
        model.addAttribute("project", project);
        model.addAttribute("clientId", clientId);

        return "project_detail";
    }

    @GetMapping("/create")
    public String createProject(@PathVariable("clientId") long clientId, Model model) {
        Project project = new Project();
        model.addAttribute("project", project);
        model.addAttribute("clientId", clientId);
        model.addAttribute("mode", Mode.NEW);
        model.addAttribute("url", urlFactory.newProject(clientId));

        return "project_form";
    }

    @PostMapping("/create")
    public String saveProject(@Valid @ModelAttribute Project project,
                              BindingResult bindingResult,
                              @PathVariable("clientId") long clientId,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("clientId", clientId);
            model.addAttribute("mode", Mode.NEW);
            model.addAttribute("url", urlFactory.newProject(clientId));
            return "project_form";
        }
        Project savedProject = projectService.save(project);
        return "redirect:" + savedProject.getLink() + "?createSuccess";
    }

    @GetMapping("/{projectId}/edit")
    public String editProject(@PathVariable("clientId") long clientId,
                              @PathVariable("projectId") long projectId,
                              Model model) {
        Project project = projectService.find(projectId);
        model.addAttribute("project", project);
        model.addAttribute("client", clientId);
        model.addAttribute("mode", Mode.EDIT);
        model.addAttribute("url", urlFactory.editProject(clientId, projectId));
        return "project_form";
    }

    @PostMapping("/{projectId}/edit")
    public String editProject(@Valid @ModelAttribute Project project,
                              BindingResult bindingResult,
                              @PathVariable("clientId") long clientId,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("client", clientId);
            model.addAttribute("mode", Mode.EDIT);
            model.addAttribute("url", urlFactory.editProject(clientId, project.getId()));
            return "project_form";
        }
        projectService.save(project);
        return "redirect:/clients/" + clientId + "/projects/" + project.getId() + "?editSuccess";
    }

    @PostMapping("/{projectId}/delete")
    public String deleteDocumentLine(@PathVariable("clientId") long clientId,
                                     @PathVariable("projectId") long projectId) {
        projectService.delete(projectId);
        return "redirect:/clients/" + clientId;
    }
}
