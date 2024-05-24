package com.example.datecalculator.controller;

import com.example.datecalculator.dto.DateRequestDto;
import com.example.datecalculator.dto.DateResponseDto;
import com.example.datecalculator.service.DateCalculatorService;
import org.json.JSONObject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
//    public ResponseEntity<DateResponseDto> getDateDiff(@RequestParam("firstDate") String firstDate, @RequestParam("secondDate") String secondDate) {
//        long diffInDays = dateCalculatorService.calculatorDateDiff(new DateRequestDto(firstDate, secondDate));
//        JSONObject json = new JSONObject();
//        json.put("diffInDays", diffInDays);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(new DateResponseDto(diffInDays));
//    }

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
