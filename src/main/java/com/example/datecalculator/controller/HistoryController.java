package com.example.datecalculator.controller;

import com.example.datecalculator.model.History;
import com.example.datecalculator.service.HistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HistoryController {
    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/getHistoryByUserId/{userId}")
    public ResponseEntity<List<History>> getHistoryByUserId(@PathVariable(name = "id") Long id) {
        List<History> history = historyService.getHistoryByUserId(id);
        return history != null ? ResponseEntity.ok(history) : ResponseEntity.notFound().build();
    }

    @PostMapping("/postHistory")
    public ResponseEntity<History> addHistory(@RequestBody History history) {
        History createdHistory = historyService.addHistory(history);
        return ResponseEntity.ok(createdHistory);
    }

    @DeleteMapping("/deleteHistory/{id}")
    public ResponseEntity<Void> deleteAbility(@PathVariable(name = "id") Long id) {
        boolean isDeleted = historyService.deleteHistory(id);
        return isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/updateHistory/{id}")
    public ResponseEntity<History> updateAbility(@RequestBody History history, @PathVariable(name = "id") Long id) {
        History updatedHistory = historyService.updateHistory(id, history);
        return updatedHistory != null ? ResponseEntity.ok(updatedHistory) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getHistoryById/{id}")
    public ResponseEntity<History> getHistoryById(@PathVariable(name = "id") Long id) {
        History history = historyService.findById(id);
        return history != null ? ResponseEntity.ok(history) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getAllHistories")
    public List<History> getALL() {
        return historyService.getAllHistories();
    }
}
