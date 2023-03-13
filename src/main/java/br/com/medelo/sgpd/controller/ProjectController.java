package br.com.medelo.sgpd.controller;

import br.com.medelo.sgpd.exception.BusinessException;
import br.com.medelo.sgpd.dto.form.OperationProjectForm;
import br.com.medelo.sgpd.dto.form.ProjectForm;
import br.com.medelo.sgpd.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/projects")
public class ProjectController {

    @Autowired
    private ProjectService service;

    private static final String ERROR = "error";
    private static final String PROJECT = "project";
    private static final String REDIRECT_PROJECTS = "redirect:/projects";

    @GetMapping
    public String showListProject(Model model, HttpServletRequest request) {

        int itemsPerPage = 10;
        String pageParam = request.getParameter("page");
        int currentPage = (pageParam != null) ? Integer.parseInt(pageParam) : 0;

        PagedListHolder<ProjectForm> pagedListHolder = new PagedListHolder<>(service.findAllProject());
        pagedListHolder.setPageSize(itemsPerPage);
        pagedListHolder.setPage(currentPage);

        List<ProjectForm> itemListForPage = pagedListHolder.getPageList();
        int totalPages = pagedListHolder.getPageCount();

        model.addAttribute("itemList", itemListForPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("projects", pagedListHolder);

        return "list";
    }

    @GetMapping(value = "/create")
    public String showCreateProject(Model model) {
        model.addAttribute(PROJECT, service.getCreateProjetoForm().withReadOnly(false));
        return "create";
    }

    @PostMapping(value = "/create")
    public String createProject(@ModelAttribute(PROJECT) OperationProjectForm project, BindingResult result) {
        if (result.hasErrors()) {
            return ERROR;
        }

        service.createProject(project);
        return REDIRECT_PROJECTS;
    }

    @GetMapping(value = "/{id}")
    public String showViewProjectPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute(PROJECT, service.findById(id).withReadOnly(true));
        return "view";
    }

    @GetMapping(value = "/{id}/update")
    public String showUpdateProject(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute(PROJECT, service.findById(id).withReadOnly(false));
        return "update";
    }

    @PostMapping(value = "/{id}/update")
    public String updateProject(@PathVariable("id") Long id, @ModelAttribute(PROJECT) OperationProjectForm project,
                                BindingResult result) {
        if (result.hasErrors()) {
            return ERROR;
        }

        service.updateProject(id, project);
        return REDIRECT_PROJECTS;
    }

    @PostMapping(value = "/{id}/delete")
    public String deleteProject(@PathVariable("id") Long id, Model model) {
        try {
            service.deleteProject(id);
        } catch (BusinessException e) {
            return ERROR;
        }
        return REDIRECT_PROJECTS;
    }
}
