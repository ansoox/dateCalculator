package com.example.dateCalculator;

import java.time.LocalDate;
import java.time.Duration;

import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DateCalculatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(DateCalculatorApplication.class, args);
    }

    @GetMapping("/date-diff")
    public ResponseEntity<String> getDateDiff(@RequestParam("date1") String date1, @RequestParam("date2") String date2) {
        LocalDate d1 = LocalDate.parse(date1);
        LocalDate d2 = LocalDate.parse(date2);
        long diffInMillis = Math.abs(Duration.between(d1.atStartOfDay(), d2.atStartOfDay()).toMillis());
        long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);

        JSONObject json = new JSONObject();
        json.put("diffInDays", diffInDays);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(json.toString());
    }
}

