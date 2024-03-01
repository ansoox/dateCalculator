package com.example.date.service;

import com.example.date.dto.DateRequestDto;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;

@Service
public class DateCalculatorService {
    public long calculatorDateDiff (DateRequestDto request){
        LocalDate d1 = LocalDate.parse(request.getFirstDate());
        LocalDate d2 = LocalDate.parse(request.getSecondDate());
        long diffInMillis = Math.abs(Duration.between(d1.atStartOfDay(), d2.atStartOfDay()).toMillis());
        return diffInMillis / (24 * 60 * 60 * 1000);
    }
}
