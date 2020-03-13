package com.frans.bcmanager.controller;

import com.frans.bcmanager.model.Enterprise;
import com.frans.bcmanager.service.EnterpriseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/enterprise")
public class EnterpriseController {

    private final EnterpriseService enterpriseService;

    public EnterpriseController(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @GetMapping
    public String showEnterprise(Model model) {
        Optional<Enterprise> enterprise = enterpriseService.getEnterprise();
        if (enterprise.isPresent()) {
            model.addAttribute("enterprise", enterprise);
        } else {
            model.addAttribute("enterprise", new Enterprise());
        }
        return "enterprise";
    }

    @PostMapping
    public String editEnterprise(@ModelAttribute @Valid Enterprise enterprise,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            return "enterprise";
        }
        enterpriseService.save(enterprise);
        return "redirect:/enterprise?updateSuccess";
    }
}
