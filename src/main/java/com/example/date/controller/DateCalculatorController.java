package com.example.date.controller;

import com.example.date.dto.DateRequestDto;
import com.example.date.dto.DateResponseDto;
import com.example.date.service.DateCalculatorService;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DateCalculatorController {

    private final DateCalculatorService dateCalculatorService;

    public DateCalculatorController(DateCalculatorService dateCalculatorService) {
        this.dateCalculatorService = dateCalculatorService;
    }

    @GetMapping("/calculate") //endpoint
    public ResponseEntity<DateResponseDto> getDateDiff (@RequestParam("date1") String date1, @RequestParam("date2") String date2){
        long diffInDays = dateCalculatorService.calculatorDateDiff(new DateRequestDto(date1, date2));
        JSONObject json = new JSONObject();
        json.put("diffInDays", diffInDays);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(new DateResponseDto(diffInDays));
    }
}
