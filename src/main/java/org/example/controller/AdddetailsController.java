package org.example.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class AdddetailsController {

    @GetMapping("/details")
    public String adddetails(Model model) {
        model.addAttribute("detail", "Student Details Page");
        return "details";
    }
}
