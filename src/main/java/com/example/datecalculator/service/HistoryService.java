package com.example.datecalculator.service;

import org.springframework.stereotype.Service;

import com.example.datecalculator.model.History;
import com.example.datecalculator.model.User;
import com.example.datecalculator.dto.HistoryDto;
import com.example.datecalculator.repository.HistoryRepository;
import com.example.datecalculator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

import java.util.List;

@Service
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;

    @Autowired
    public HistoryService(HistoryRepository historyRepository, UserRepository userRepository) {
        this.historyRepository = historyRepository;
        this.userRepository = userRepository;
    }

    public History addHistory(HistoryDto historyDto) {
        User user = userRepository.findById(historyDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        History history = new History();
        history.setFirstDate(historyDto.getFirstDate());
        history.setSecondDate(historyDto.getSecondDate());
        history.setDiffInDays(historyDto.getDiffInDays());
        history.setUser(user);
        history.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        history.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return historyRepository.save(history);
    }

    public List<History> getHistoryByUserId(Long userId) {
        return historyRepository.findByUserId(userId);
    }

    public History updateHistory(Long id, HistoryDto historyDto) {
        History history = historyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("History not found"));
        history.setFirstDate(historyDto.getFirstDate());
        history.setSecondDate(historyDto.getSecondDate());
        history.setDiffInDays(historyDto.getDiffInDays());
        history.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return historyRepository.save(history);
    }

    public boolean deleteHistory(Long historyId) {
        if (historyRepository.existsById(historyId)) {
            historyRepository.deleteById(historyId);
            return true;
        }
        return false;
    }

    public List<History> getAllHistories() {
        return historyRepository.findAll();
    }

    public History findById(Long id) {
        return historyRepository.findById(id).orElse(null);
    }

}
