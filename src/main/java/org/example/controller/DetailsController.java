package org.example.controller;

import org.example.model.Details;
import org.example.repository.DetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/details")
public class DetailsController {

    @Autowired
    private DetailsRepository detailsRepository;

    @GetMapping
    public String listDetails(Model model) {
        model.addAttribute("detailsList", detailsRepository.findAll());
        return "details-list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("details", new Details());
        return "add-details";
    }

    @PostMapping
    public String saveDetails(@ModelAttribute Details details) {
        detailsRepository.save(details);
        return "redirect:/details";
    }

    @GetMapping("/delete/{id}")
    public String deleteDetails(@PathVariable Long id) {
        detailsRepository.deleteById(id);
        return "redirect:/details";
    }
}
