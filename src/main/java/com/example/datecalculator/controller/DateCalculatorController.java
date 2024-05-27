package com.example.datecalculator.controller;

import com.example.datecalculator.dto.DateRequestDto;
import com.example.datecalculator.service.DateCalculatorService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DateCalculatorController {

    private final DateCalculatorService dateCalculatorService;

    public DateCalculatorController(DateCalculatorService dateCalculatorService) {
        this.dateCalculatorService = dateCalculatorService;
    }

    @GetMapping("/")
    public String DateCalculatorHome(Model model){
        model.addAttribute("title", "Date Calculator");
        return "DateCalculatorHome";
    }

    @PostMapping("/")
    public String calculateDateDiff(DateRequestDto request, Model model) {
        long daysDiff = dateCalculatorService.calculatorDateDiff(request);
        model.addAttribute("result", "The difference between the dates: " + daysDiff + ".");
        return "DateCalculatorHome";
    }

    @GetMapping("/about")
    public String About(Model model){
        model.addAttribute("title", "About");
        return "About";
    }
}
