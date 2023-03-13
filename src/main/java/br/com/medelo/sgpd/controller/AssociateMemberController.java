package br.com.medelo.sgpd.controller;

import br.com.medelo.sgpd.dto.form.AssociateForm;
import br.com.medelo.sgpd.service.MemberService;
import br.com.medelo.sgpd.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/members")
public class AssociateMemberController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private MemberService memberService;

    @GetMapping(value = "/associate")
    public String showAssociateMembersPage(Model model) {
        model.addAttribute("projects", projectService.findAllProject());
        model.addAttribute("members", memberService.findAllEmployee());
        model.addAttribute("associate", AssociateForm.builder().build());
        return "associate";
    }

    @PostMapping(value = "/associate")
    public String associateMembersProjects(@ModelAttribute("associate") @Valid AssociateForm associate, BindingResult result) {
        if (result.hasErrors()) {
            return "error";
        }

        memberService.associateMemberProject(associate.getIdMember(), associate.getIdProject());
        return "redirect:/projects";
    }
}
