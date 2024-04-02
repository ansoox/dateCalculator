package com.example.datecalculator.service;

import org.springframework.stereotype.Service;

import com.example.datecalculator.model.History;
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

    public History addHistory(History history) {
        history.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        history.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return historyRepository.save(history);
    }

    public List<History> getHistoryByUserId(Long userId) {
        return historyRepository.findByUserId(userId);
    }

    public History updateHistory(Long historyId, History updatedHistory) {
        return historyRepository.findById(historyId)
                .map(history -> {
                    history.setFirstDate(updatedHistory.getFirstDate());
                    history.setSecondDate(updatedHistory.getSecondDate());
                    history.setDiffInDays(updatedHistory.getDiffInDays());
                    history.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                    return historyRepository.save(history);
                }).orElse(null);
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
