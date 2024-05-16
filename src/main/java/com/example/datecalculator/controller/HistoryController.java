package com.example.datecalculator.controller;

import com.example.datecalculator.model.History;
import com.example.datecalculator.dto.HistoryDto;
import com.example.datecalculator.service.HistoryService;
import com.example.datecalculator.service.RequestCounterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HistoryController {
    private final HistoryService historyService;
    private final RequestCounterService counterService;

    public HistoryController(HistoryService historyService, RequestCounterService counterService) {
        this.historyService = historyService;
        this.counterService = counterService;
    }

    @PostMapping("/postHistory")
    public ResponseEntity<History> addHistory(@RequestBody HistoryDto historyDto) {
        counterService.requestIncrement();
        History createdHistory = historyService.addHistory(historyDto);
        return ResponseEntity.ok(createdHistory);
    }

    @DeleteMapping("/deleteHistory/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable(name = "id") Long id) {
        counterService.requestIncrement();
        boolean isDeleted = historyService.deleteHistory(id);
        return isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/updateHistory/{id}")
    public ResponseEntity<History> updateHistory(@RequestBody HistoryDto historyDto, @PathVariable(name = "id") Long id) {
        counterService.requestIncrement();
        History updatedHistory = historyService.updateHistory(id, historyDto);
        return updatedHistory != null ? ResponseEntity.ok(updatedHistory) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getHistory/{id}")
    public ResponseEntity<History> getHistoryById(@PathVariable(name = "id") Long id) {
        counterService.requestIncrement();
        History history = historyService.findById(id);
        return history != null ? ResponseEntity.ok(history) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getAllHistories")
    public List<History> getALL() {
        counterService.requestIncrement();
        return historyService.getAllHistories();
    }
}
